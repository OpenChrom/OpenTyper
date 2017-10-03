/*******************************************************************************
 * Copyright (c) 2014, 2017 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import net.openchrom.msd.identifier.supplier.opentyper.ui.wizards.MassSpectrumBatchProcessJobWizard;

public class MassSpectrumBatchProcessHandler {

	@Execute
	public void execute() {

		WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), new MassSpectrumBatchProcessJobWizard());
		wizardDialog.open();
	}
}
