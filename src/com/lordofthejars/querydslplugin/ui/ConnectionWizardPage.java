package com.lordofthejars.querydslplugin.ui;

import static com.lordofthejars.querydslplugin.util.StringUtils.hasText;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ConnectionWizardPage extends WizardPage {

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

		Label driverLocationLabel = new Label(container, SWT.NULL);
		driverLocationLabel.setText("Jdbc Jar Location");
		driverLocation = new Text(container, SWT.BORDER | SWT.SINGLE);
		driverLocation.setText("");
		driverLocation.setLayoutData(gd);
		driverLocationLabel.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				//TODO change when driver location is set using Browser button.
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		Label driverClassNameLabel = new Label(container, SWT.NULL);
		driverClassNameLabel.setText("Driver Class Name");
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
		databaseUrlLabel.setText("Database Url");
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
		databaseUserLabel.setText("Database Username");
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
		databasePasswordLabel.setText("Database Password");
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
