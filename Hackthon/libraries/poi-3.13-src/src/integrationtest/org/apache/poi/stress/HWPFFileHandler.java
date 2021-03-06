/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package org.apache.poi.stress;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.poi.hdf.extractor.WordDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class HWPFFileHandler extends POIFSFileHandler {
	@Override
	public void handleFile(InputStream stream) throws Exception {
		HWPFDocument doc = new HWPFDocument(stream);
		assertNotNull(doc.getBookmarks());
		assertNotNull(doc.getCharacterTable());
		assertNotNull(doc.getEndnotes());

		handlePOIDocument(doc);

		// fails for many documents, but is deprecated anyway...
		// handleWordDocument(doc);
	}

	protected void handleWordDocument(HWPFDocument doc) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		doc.write(outStream);

		WordDocument wordDoc = new WordDocument(new ByteArrayInputStream(outStream.toByteArray()));

        StringWriter docTextWriter = new StringWriter();
        PrintWriter out = new PrintWriter(docTextWriter);
        try {
        	wordDoc.writeAllText(out);
        } finally {
        	out.close();
        }
        docTextWriter.close();
	}

	// a test-case to test this locally without executing the full TestAllFiles
	@Test
	public void test() throws Exception {
		File file = new File("test-data/document/52117.doc");

		InputStream stream = new FileInputStream(file);
		try {
			handleFile(stream);
		} finally {
			stream.close();
		}

		handleExtracting(file);

		stream = new FileInputStream(file);
		try {
			WordExtractor extractor = new WordExtractor(stream);
			try {
				assertNotNull(extractor.getText());
			} finally {
				extractor.close();
			}
		} finally {
			stream.close();
		}
	}

	@Test
	public void testExtractingOld() throws Exception {
		File file = new File("test-data/document/52117.doc");
		handleExtracting(file);
	}
}
