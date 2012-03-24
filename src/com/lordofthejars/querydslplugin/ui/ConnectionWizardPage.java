package com.lordofthejars.querydslplugin.ui;

import static com.lordofthejars.querydslplugin.util.StringUtils.hasText;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.lordofthejars.querydslplugin.Messages;

public class ConnectionWizardPage extends WizardPage {

	
	private static final String[] JAR_EXT = new String[]{"*.jar"}; 
	
	private Composite container;
	private Text driverClassName;
	private Text databaseUrl;
	private Text databaseUser;
	private Text databasePassword;
	private Text driverLocation;

	
	
	protected ConnectionWizardPage(String pageName, String description) {
		super(pageName);
		setTitle(pageName);
		setDescription(description);
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		
		
		Label driverClassNameLabel = new Label(container, SWT.NULL);
		driverClassNameLabel.setText(Messages.DriverClassName_label);
		driverClassName = new Text(container, SWT.BORDER | SWT.SINGLE);
		driverClassName.setText("");
		driverClassName.setLayoutData(gd);
		driverClassName.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		Label databaseUrlLabel = new Label(container, SWT.NULL);
		databaseUrlLabel.setText(Messages.DatabaseUrl_label);
		databaseUrl = new Text(container, SWT.BORDER | SWT.SINGLE);
		databaseUrl.setText("");
		databaseUrl.setLayoutData(gd);
		databaseUrl.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		Label databaseUserLabel = new Label(container, SWT.NULL);
		databaseUserLabel.setText(Messages.DatabaseUser_label);
		databaseUser = new Text(container, SWT.BORDER | SWT.SINGLE);
		databaseUser.setText("");
		databaseUser.setLayoutData(gd);
		databaseUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		Label databasePasswordLabel = new Label(container, SWT.NULL);
		databasePasswordLabel.setText(Messages.DatabasePassword_label);
		databasePassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		databasePassword.setText("");
		databasePassword.setLayoutData(gd);
		databasePassword.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		
		Button button = new Button(container, SWT.NULL);
		button.setText(Messages.Browser_button);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileDialog = new FileDialog(container.getShell(), SWT.OPEN);
				fileDialog.setFilterExtensions(JAR_EXT);
				String driverPath = fileDialog.open();
				
				if(driverPath != null) {
					driverLocation.setText(driverPath);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		driverLocation = new Text(container, SWT.BORDER | SWT.SINGLE);
		driverLocation.setText("");
		driverLocation.setLayoutData(gd);
		driverLocation.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				validate();
			}
		});
		
		
		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	protected void validate() {

		if(!validateDriverLocation()) {
			return;
		}

		if(!validateDriverClassName()) {
			return;
		}

		if(!validateDatabaseUrl()) {
			return;
		}
		
		if(!validateDatabaseUser()) {
			return;
		}
		
		if(!validateDatabasePassword()) {
			return;
		}
		
		setPageComplete(true);
		
	}

	private boolean validateDatabasePassword() {
		return hasText(this.databasePassword.getText());
	}
	
	private boolean validateDatabaseUser() {
		return hasText(this.databaseUser.getText());
	}
	
	private boolean validateDatabaseUrl() {
		return hasText(this.databaseUrl.getText());
	}
	
	private boolean validateDriverClassName() {
		return 	hasText(this.driverClassName.getText());
	}
	
	private boolean validateDriverLocation() {
		return hasText(this.driverLocation.getText());
	}


	public Text getDriverClassName() {
		return driverClassName;
	}

	public Text getDatabaseUrl() {
		return databaseUrl;
	}

	public Text getDatabaseUser() {
		return databaseUser;
	}

	public Text getDatabasePassword() {
		return databasePassword;
	}

	public Text getDriverLocation() {
		return driverLocation;
	}

	
}
