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
package net.openchrom.msd.identifier.supplier.opentyper.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Matthias Mailänder
 * 
 */
public class MassSpectrumBatchProcessJob implements IMassSpectrumBatchProcessJob {

	private List<IMassSpectrumInputEntry> massSpectrumInputEntries;
	private List<IMassSpectrumOutputEntry> massSpectrumOutputEntries;

	/**
	 * Creates a new batch process job.
	 */
	public MassSpectrumBatchProcessJob() {
		/*
		 * Why are ArrayLists used here?
		 * The entries shall be processed in the order the user has chosen them.
		 */
		massSpectrumInputEntries = new ArrayList<IMassSpectrumInputEntry>();
		massSpectrumOutputEntries = new ArrayList<IMassSpectrumOutputEntry>();
	}

	@Override
	public List<IMassSpectrumInputEntry> getMassSpectrumInputEntries() {

		return massSpectrumInputEntries;
	}

	@Override
	public List<IMassSpectrumOutputEntry> getMassSpectrumOutputEntries() {

		return massSpectrumOutputEntries;
	}
}
