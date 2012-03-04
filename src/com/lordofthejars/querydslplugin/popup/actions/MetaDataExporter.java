package com.lordofthejars.querydslplugin.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.lordofthejars.querydslplugin.ui.ExportMetaDataWizard;

public class MetaDataExporter implements IObjectActionDelegate {

	private IWorkbenchWindow workbenchWindow;
	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public MetaDataExporter() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
		this.workbenchWindow = targetPart.getSite().getWorkbenchWindow();
		this.shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@Override
	public void run(final IAction action) {
		
		WizardDialog wizardDialog = new WizardDialog(shell, new ExportMetaDataWizard(this.workbenchWindow.getSelectionService()));
		wizardDialog.open();
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
	}

}
