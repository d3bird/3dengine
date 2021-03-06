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
import java.io.InputStream;

import org.apache.poi.POIDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class POIFSFileHandler extends AbstractFileHandler {

	@Override
    public void handleFile(InputStream stream) throws Exception {
		POIFSFileSystem fs = new POIFSFileSystem(stream);
		handlePOIFSFileSystem(fs);
	}

	private void handlePOIFSFileSystem(POIFSFileSystem fs) {
		assertNotNull(fs);
		assertNotNull(fs.getRoot());
	}

	protected void handlePOIDocument(POIDocument doc) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		doc.write(out);

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		POIFSFileSystem fs = new POIFSFileSystem(in);
		handlePOIFSFileSystem(fs);
	}
}
