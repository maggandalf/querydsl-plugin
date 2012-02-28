package com.lordofthejars.querydslplugin.ui;

import org.eclipse.jface.wizard.Wizard;

public class ExportMetaDataWizard extends Wizard {

	protected ConnectionWizardPage connectionWizardPage;
	
	public ExportMetaDataWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	
	
	@Override
	public void addPages() {
		connectionWizardPage = new ConnectionWizardPage("Jdbc Properties", "Jdbc Properties");
		addPage(connectionWizardPage);
	}



	@Override
	public boolean performFinish() {
	
		//call Export Metadata with configured data.
		System.out.println("MetaData called.");
		return false;
	}

}
