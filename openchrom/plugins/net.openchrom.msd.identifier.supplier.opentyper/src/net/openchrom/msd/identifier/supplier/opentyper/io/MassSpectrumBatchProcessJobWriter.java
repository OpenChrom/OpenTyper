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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.identifier.supplier.opentyper.internal.support.IMassSpectrumBatchProcessJobTags;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumBatchProcessJob;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumInputEntry;
import net.openchrom.msd.identifier.supplier.opentyper.model.IMassSpectrumOutputEntry;

/**
 * @author Matthias Mailänder
 * 
 */
public class MassSpectrumBatchProcessJobWriter implements IMassSpectrumBatchProcessJobWriter {

	@Override
	public void writeBatchProcessJob(File file, IMassSpectrumBatchProcessJob batchProcessJob, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException, XMLStreamException {

		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
		XMLEventWriter eventWriter = xmlOutputFactory.createXMLEventWriter(bufferedOutputStream, IMassSpectrumBatchProcessJobTags.UTF8);
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		/*
		 * Document
		 */
		eventWriter.add(eventFactory.createStartDocument());
		StartElement massSpectrumStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.BATCH_PROCESS_JOB);
		eventWriter.add(massSpectrumStart);
		/*
		 * Write the header and the list informations.
		 */
		writeBatchProcessJobHeader(eventWriter, eventFactory, batchProcessJob);
		writeComment(eventWriter, eventFactory, "Load the following mass spectra.");
		writeMassSpectrumInputEntries(eventWriter, eventFactory, batchProcessJob.getMassSpectrumInputEntries());
		writeComment(eventWriter, eventFactory, "Write each mass spectrum to the given output formats.");
		writeMassSpectrumOutputEntries(eventWriter, eventFactory, batchProcessJob.getMassSpectrumOutputEntries());
		/*
		 * Close the document
		 */
		EndElement massSpectrumEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.BATCH_PROCESS_JOB);
		eventWriter.add(massSpectrumEnd);
		eventWriter.add(eventFactory.createEndDocument());
		/*
		 * Close the streams
		 */
		bufferedOutputStream.flush();
		eventWriter.flush();
		bufferedOutputStream.close();
		eventWriter.close();
	}

	/**
	 * Writes the comment.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @throws XMLStreamException
	 */
	private void writeComment(XMLEventWriter eventWriter, XMLEventFactory eventFactory, String comment) throws XMLStreamException {

		/*
		 * Comment
		 */
		Comment batchJobInfo = eventFactory.createComment(comment);
		eventWriter.add(batchJobInfo);
	}

	/**
	 * Writes the header.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @param batchProcessJob
	 * @throws XMLStreamException
	 */
	private void writeBatchProcessJobHeader(XMLEventWriter eventWriter, XMLEventFactory eventFactory, IMassSpectrumBatchProcessJob batchProcessJob) throws XMLStreamException {

		/*
		 * Element and content definition.
		 */
		StartElement headerStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.HEADER);
		EndElement headerEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.HEADER);
		/*
		 * Write the elements.
		 */
		eventWriter.add(headerStart);
		eventWriter.add(headerEnd);
	}

	/**
	 * Writes the mass spectrum input entries.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @param inputEntries
	 * @throws XMLStreamException
	 */
	private void writeMassSpectrumInputEntries(XMLEventWriter eventWriter, XMLEventFactory eventFactory, List<IMassSpectrumInputEntry> inputEntries) throws XMLStreamException {

		/*
		 * Element and content definition.
		 */
		StartElement entriesStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_INPUT_ENTRIES);
		EndElement entriesEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_INPUT_ENTRIES);
		/*
		 * Write the elements.
		 */
		eventWriter.add(entriesStart);
		for(IMassSpectrumInputEntry inputEntry : inputEntries) {
			writeMassSpectrumInputEntry(eventWriter, eventFactory, inputEntry);
		}
		eventWriter.add(entriesEnd);
	}

	/**
	 * Writes the mass spectrum input entry.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @param inputEntry
	 * @throws XMLStreamException
	 */
	private void writeMassSpectrumInputEntry(XMLEventWriter eventWriter, XMLEventFactory eventFactory, IMassSpectrumInputEntry inputEntry) throws XMLStreamException {

		/*
		 * Element and content definition.
		 */
		StartElement entryStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_INPUT_ENTRY);
		EndElement entryEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_INPUT_ENTRY);
		/*
		 * Values.
		 */
		Characters inputFile = eventFactory.createCData(inputEntry.getInputFile());
		/*
		 * Write the elements.
		 */
		eventWriter.add(entryStart);
		eventWriter.add(inputFile);
		eventWriter.add(entryEnd);
	}

	/**
	 * Writes the mass spectrum output entries.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @param inputEntries
	 * @throws XMLStreamException
	 */
	private void writeMassSpectrumOutputEntries(XMLEventWriter eventWriter, XMLEventFactory eventFactory, List<IMassSpectrumOutputEntry> outputEntries) throws XMLStreamException {

		/*
		 * Element and content definition.
		 */
		StartElement entriesStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_OUTPUT_ENTRIES);
		EndElement entriesEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_OUTPUT_ENTRIES);
		/*
		 * Write the elements.
		 */
		eventWriter.add(entriesStart);
		for(IMassSpectrumOutputEntry outputEntry : outputEntries) {
			writeMassSpectrumOutputEntry(eventWriter, eventFactory, outputEntry);
		}
		eventWriter.add(entriesEnd);
	}

	/**
	 * Writes the mass spectrum output entry.
	 * 
	 * @param eventWriter
	 * @param eventFactory
	 * @param inputEntry
	 * @throws XMLStreamException
	 */
	private void writeMassSpectrumOutputEntry(XMLEventWriter eventWriter, XMLEventFactory eventFactory, IMassSpectrumOutputEntry outputEntry) throws XMLStreamException {

		/*
		 * Element and content definition.
		 */
		StartElement entryStart = eventFactory.createStartElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_OUTPUT_ENTRY);
		EndElement entryEnd = eventFactory.createEndElement("", "", IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_OUTPUT_ENTRY);
		/*
		 * Attributes and Values.
		 */
		Attribute converterId = eventFactory.createAttribute(IMassSpectrumBatchProcessJobTags.MASSSPECTRUM_CONVERTER_ID, outputEntry.getConverterId());
		Characters outputFolder = eventFactory.createCData(outputEntry.getOutputFolder());
		/*
		 * Write the elements.
		 */
		eventWriter.add(entryStart);
		eventWriter.add(converterId);
		eventWriter.add(outputFolder);
		eventWriter.add(entryEnd);
	}
}
