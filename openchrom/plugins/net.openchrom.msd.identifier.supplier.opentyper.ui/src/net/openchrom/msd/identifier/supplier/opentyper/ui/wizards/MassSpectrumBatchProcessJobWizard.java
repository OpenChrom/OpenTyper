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
package net.openchrom.msd.identifier.supplier.opentyper.ui.wizards;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.wizards.AbstractFileWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.identifier.supplier.opentyper.io.MassSpectrumBatchProcessJobWriter;
import net.openchrom.msd.identifier.supplier.opentyper.model.MassSpectrumBatchProcessJob;

public class MassSpectrumBatchProcessJobWizard extends AbstractFileWizard {

	private static final Logger logger = Logger.getLogger(MassSpectrumBatchProcessJobWizard.class);

	public MassSpectrumBatchProcessJobWizard() {
		super("BatchJob", ".obj");
	}

	@Override
	public void doFinish(IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Create Batch Job", IProgressMonitor.UNKNOWN);
		final IFile file = super.prepareProject(monitor);
		/*
		 * Initialize a simple batch process job.
		 */
		try {
			MassSpectrumBatchProcessJob batchProcessJob = new MassSpectrumBatchProcessJob();
			MassSpectrumBatchProcessJobWriter batchProcessJobWriter = new MassSpectrumBatchProcessJobWriter();
			batchProcessJobWriter.writeBatchProcessJob(file.getLocation().toFile(), batchProcessJob, monitor);
		} catch(FileNotFoundException e) {
			logger.warn(e);
		} catch(FileIsNotWriteableException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		} catch(XMLStreamException e) {
			logger.warn(e);
		}
		/*
		 * Refresh
		 */
		super.refreshWorkspace(monitor);
		super.runOpenEditor(file, monitor);
	}
}
