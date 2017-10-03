/*******************************************************************************
 * Copyright (c) 2010, 2017 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumBatchProcessJob;

/**
 * @author Matthias Mailänder
 * 
 */
public interface IMassSpectrumBatchProcessJobWriter {

	void writeBatchProcessJob(File file, IMassSpectrumBatchProcessJob batchProcessJob, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException, XMLStreamException;
}
