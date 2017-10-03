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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.chemclipse.chromatogram.msd.process.supplier.batchprocess.internal.filter.BatchProcessJobEventFilter;
import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.identifier.supplier.opentyper.internal.support.IMassSpectrumBatchProcessJobTags;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumBatchProcessJob;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumInputEntry;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumOutputEntry;
import net.openchrom.msd.identifier.supplier.opentyper.model.MassSpectrumBatchProcessJob;
import net.openchrom.msd.identifier.supplier.opentyper.model.MassSpectrumInputEntry;
import net.openchrom.msd.identifier.supplier.opentyper.model.MassSpectrumOutputEntry;

/**
 * @author Matthias Mailänder
 * 
 */
public class MassSpectrumBatchProcessJobReader implements IMassSpectrumBatchProcessJobReader {

	@Override
	public IMassSpectrumBatchProcessJob read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectrumBatchProcessJob batchProcessJob = new MassSpectrumBatchProcessJob();
		try {
			readHeader(file, batchProcessJob);
			readMassSpectrumInputEntries(file, batchProcessJob, monitor);
			readMassSpectrumOutputEntries(file, batchProcessJob, monitor);
		} catch(XMLStreamException e) {
			throw new IOException(e);
		}
		return batchProcessJob;
	}

	/**
	 * Reads the header information.
	 * 
	 * @throws IOException
	 */
	private void readHeader(File file, IMassSpectrumBatchProcessJob batchProcessJob) throws XMLStreamException, IOException {

		/*
		 * Open the streams.
		 */
		XMLEventReader eventReader;
		XMLInputFactory.newInstance().setProperty(XMLInputFactory.IS_COALESCING, true);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		eventReader = inputFactory.createXMLEventReader(bufferedInputStream, IMassSpectrumBatchProcessJobTags.UTF8);
		XMLEvent event;
		String elementName;
		exitloop:
		while(eventReader.hasNext()) {
			event = eventReader.nextEvent();
			if(event.isStartElement()) {
				elementName = event.asStartElement().getName().getLocalPart();
				/*
				 * Read the header elements. There are none to read actually.
				 */
			} else {
				/*
				 * Check if it is the end of the header. If yes, break to not
				 * run through all elements of the XML document.
				 */
				if(event.isEndElement()) {
					elementName = event.asEndElement().getName().getLocalPart();
					if(elementName.equals(IMassSpectrumBatchProcessJobTags.HEADER)) {
						break exitloop;
					}
				}
			}
		}
		/*
		 * Close the streams.
		 */
		eventReader.close();
		bufferedInputStream.close();
	}

	/**
	 * Read the mass spectrum input entries.
	 * 
	 * @param file
	 * @param batchProcessJob
	 * @param monitor
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	private void readMassSpectrumInputEntries(File file, IMassSpectrumBatchProcessJob batchProcessJob, IProgressMonitor monitor) throws XMLStreamException, IOException {

		IMassSpectrumInputEntry inputEntry;
		XMLEvent event;
		/*
		 * Open the streams.
		 */
		XMLEventReader eventReader;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		eventReader = inputFactory.createXMLEventReader(bufferedInputStream, IMassSpectrumBatchProcessJobTags.UTF8);
		/*
		 * Use event filters.
		 */
		List<String> acceptedElements = new ArrayList<String>();
		acceptedElements.add(IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_INPUT_ENTRY);
		EventFilter eventFilter = new BatchProcessJobEventFilter(acceptedElements);
		XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
		/*
		 * Read all entries.
		 */
		while(filteredEventReader.hasNext()) {
			event = filteredEventReader.nextEvent();
			event = eventReader.nextEvent();
			inputEntry = new MassSpectrumInputEntry(event.asCharacters().getData());
			batchProcessJob.getMassSpectrumInputEntries().add(inputEntry);
		}
		/*
		 * Close the streams.
		 */
		eventReader.close();
		bufferedInputStream.close();
	}

	/**
	 * Reads the mass spectrum output entries.
	 * 
	 * @param file
	 * @param batchProcessJob
	 * @param monitor
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void readMassSpectrumOutputEntries(File file, IMassSpectrumBatchProcessJob batchProcessJob, IProgressMonitor monitor) throws XMLStreamException, IOException {

		IMassSpectrumOutputEntry outputEntry;
		String outputFolder;
		String converterId = "";
		XMLEvent event;
		Attribute attribute;
		String attributeName;
		/*
		 * Open the streams.
		 */
		XMLEventReader eventReader;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
		eventReader = inputFactory.createXMLEventReader(bufferedInputStream, IMassSpectrumBatchProcessJobTags.UTF8);
		/*
		 * Use event filters.
		 */
		List<String> acceptedElements = new ArrayList<String>();
		acceptedElements.add(IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_OUTPUT_ENTRY);
		EventFilter eventFilter = new BatchProcessJobEventFilter(acceptedElements);
		XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
		/*
		 * Read all entries.
		 */
		while(filteredEventReader.hasNext()) {
			event = filteredEventReader.nextEvent();
			Iterator<? extends Attribute> attributes = event.asStartElement().getAttributes();
			while(attributes.hasNext()) {
				attribute = attributes.next();
				attributeName = attribute.getName().getLocalPart();
				// Get the converter id.
				if(attributeName.equals(IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_CONVERTER_ID)) {
					converterId = attribute.getValue();
				}
			}
			// Get the output file.
			event = eventReader.nextEvent();
			outputFolder = event.asCharacters().getData();
			outputEntry = new MassSpectrumOutputEntry(outputFolder, converterId);
			batchProcessJob.getMassSpectrumOutputEntries().add(outputEntry);
		}
		/*
		 * Close the streams.
		 */
		eventReader.close();
		bufferedInputStream.close();
	}
}
