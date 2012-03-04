package com.lordofthejars.querydslplugin.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;


import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.ISelectionService;

import com.lordofthejars.querydslplugin.Messages;
import com.lordofthejars.querydslplugin.util.JarFileClassLoader;

public class ExportMetaDataWizard extends Wizard {

	public static final char SEPARATOR = File.separatorChar;
	
	private static final String SCHEMA = "PUBLIC";
	private ISelectionService iSelectionService;
	
	
	protected ConnectionWizardPage connectionWizardPage;
	
	class DriverWrapper implements Driver {
		private Driver driver;
		DriverWrapper(Driver d) {
			this.driver = d;
		}
		public boolean acceptsURL(String u) throws SQLException {
			return this.driver.acceptsURL(u);
		}
		public Connection connect(String u, Properties p) throws SQLException {
			return this.driver.connect(u, p);
		}
		public int getMajorVersion() {
			return this.driver.getMajorVersion();
		}
		public int getMinorVersion() {
			return this.driver.getMinorVersion();
		}
		public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
			return this.driver.getPropertyInfo(u, p);
		}
		public boolean jdbcCompliant() {
			return this.driver.jdbcCompliant();
		}
	}
	
	public ExportMetaDataWizard(ISelectionService iSelectionService) {
		super();
		this.iSelectionService = iSelectionService;
		setNeedsProgressMonitor(false);
	}
	
	
	
	@Override
	public void addPages() {
		connectionWizardPage = new ConnectionWizardPage("Jdbc Properties", "Jdbc Properties");
		addPage(connectionWizardPage);
	}



	@Override
	public boolean performFinish() {

		String driverClassname = connectionWizardPage.getDriverClassName().getText();
		String databaseUrl = connectionWizardPage.getDatabaseUrl().getText();
		String databaseUser = connectionWizardPage.getDatabaseUser().getText();
		String databasePassword = connectionWizardPage.getDatabasePassword().getText();
		
		IStructuredSelection structured = (IStructuredSelection) iSelectionService
                .getSelection("org.eclipse.jdt.ui.PackageExplorer");
		IPackageFragment iPackageFragment = (IPackageFragment)structured.getFirstElement();
		
		String packageName = iPackageFragment.getElementName();
		String driverLocation = connectionWizardPage.getDriverLocation().getText();
		
		try {
			loadDriverToClasspath(driverLocation, driverClassname);
		} catch (MalformedURLException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		} catch (ClassNotFoundException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		} catch (InstantiationException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		} catch (IllegalAccessException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		} catch (SQLException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		}
		
		com.mysema.query.sql.MetaDataExporter metaDataExporter = new com.mysema.query.sql.MetaDataExporter();
		metaDataExporter.setPackageName(packageName);
		metaDataExporter.setSchemaPattern(SCHEMA);
		String projectPath = getWorkspacePath(iPackageFragment);
		metaDataExporter.setTargetFolder(getSourceFolder(iPackageFragment, projectPath));
		
		try {
			metaDataExporter.export(getConnection(databaseUrl, databaseUser, databasePassword).getMetaData());
		} catch (SQLException e) {
			ErrorDialog.openError(getShell(), Messages.ErrorLoadingJar_error_msg, e.getMessage(), getStatus());
			return false;
		}
		
		return true;
	}


	private Status getStatus() {
		Status status = new Status(IStatus.ERROR, Messages.QueryDSL_msg, 0,
		            Messages.QueryDSLPlugin_error_msg, null);
		return status;
	}

	private File getSourceFolder(IPackageFragment iPackageFragment, String workspacePath) {
		IPath sourceFolder = iPackageFragment.getParent().getPath();
		String projectPath = sourceFolder.toOSString();
		
		return new File(workspacePath+projectPath);
	}

	
	
	private String getWorkspacePath(IPackageFragment iPackageFragment) {
		return iPackageFragment.getJavaProject().getProject().getWorkspace().getRoot().getRawLocation().toOSString();
	}
	
	private Connection getConnection(String databaseUrl, String databaseUser, String databasePassword) throws SQLException {
		 Connection connection = DriverManager.getConnection(databaseUrl,databaseUser,databasePassword);
		 return connection;
	}

	private void loadDriverToClasspath(String jarLocation, String driverClass) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		JarFileClassLoader jarFileLoader = new JarFileClassLoader(new URL[]{});
		jarFileLoader.loadJarFiles(jarLocation);
		jarFileLoader.loadClass(driverClass);
		Driver d = (Driver)Class.forName(driverClass, true, jarFileLoader).newInstance();
		DriverManager.registerDriver(new DriverWrapper(d));
	}

}
