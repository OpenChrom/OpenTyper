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
package net.openchrom.msd.identifier.supplier.opentyper.internal.support;

/**
 * @author Matthias Mailänder
 * 
 */
public interface IMassSpectrumBatchProcessJobTags {

	String UTF8 = "UTF-8";
	/*
	 * Start and Stop Element /
	 */
	String BATCH_PROCESS_JOB = "BatchProcessJob";
	/*
	 * Header
	 */
	String HEADER = "Header";
	String REPORT_FOLDER = "ReportFolder";
	String OVERRIDE_REPORT = "OverrideReport";
	/*
	 * Mass Spectrum Input Entries
	 */
	String MASSSPECTRUM_INPUT_ENTRIES = "InputEntries";
	String MASSSPECTRUM_INPUT_ENTRY = "InputEntry";
	/*
	 * Mass Spectrum Output Entries
	 */
	String MASSSPECTRUM_OUTPUT_ENTRIES = "OutputEntries";
	String MASSSPECTRUM_OUTPUT_ENTRY = "OutputEntry";
	String MASSSPECTRUM_CONVERTER_ID = "converterId";
}
