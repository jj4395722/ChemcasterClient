/*
* ChemcasterClient - a Java interface to the REST services of 
* http://www.chemcaster.com. Based on chemcaster-ruby
* (Copyright (c) 2009 Metamolecular LLC - http://www.metamolecular.com).
*
* Copyright (c) 2009 John Jaeger  <jj4395722_at_yahoo_dot_com>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/

package com.chemcaster.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class Zip. A zip file returned from a successful Archive request.
 * 
 * A SIDE EFFECT of the setData() method of this class is to create a temporary file in
 * the system temporary file location, containing the data passed to setData(). The file
 * is destroyed on garbage collection of this class. You can force the file to be deleted
 * by setting the instance variable to null.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Zip extends Representation {
	private static final int READ_BYTE_BUFFER_SIZE = 1024;
	private static final String ARCHIVE_FILE_PREFIX = "CCArch";
	private static final String CANNOT_REOPEN_TEMP_FILE = "Cannot reopen temporary file created earlier: ";
	private static final String NO_DATA_FOUND = "No data found either internally or in a generated temp file.";
	private static final String UNABLE_TO_OPEN_FILE = "Unable to open file for archive storage: ";
	private static final String UNABLE_TO_WRITE_TEMP_FILE = "Unable to write to temp file for archive storage: ";

	private static final String FILE_DATE_FORMAT = "yyyyMMddHHmmssSSS";

	private static final String DATA_TAG = "data";

	private String storedFileName;

	/**
	 * Instantiates a new zip.
	 */
	public Zip() {
		super();
		addNewResource(DATA_TAG);
	}

	/**
	 * Put data from a InputStream into the class.
	 * 
	 * @param dataStream
	 *            the data stream
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void setData(InputStream dataStream)
			throws ClientException {
		String timeStamp = new SimpleDateFormat(FILE_DATE_FORMAT)
				.format(new Date());

		try {
			File bufferFile = File.createTempFile(ARCHIVE_FILE_PREFIX
					+ timeStamp, ".zip");
			FileOutputStream outStream = new FileOutputStream(bufferFile,
					true);

			int bytesRead = 0;
			byte[] byteBuffer = new byte[READ_BYTE_BUFFER_SIZE];

			bytesRead = dataStream.read(byteBuffer);
			while (bytesRead > 0) {
				outStream.write(byteBuffer, 0, bytesRead);
				bytesRead = dataStream.read(byteBuffer);
			}

			storedFileName = bufferFile.getCanonicalPath();

			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			throw new ClientException(UNABLE_TO_OPEN_FILE + e.getMessage());
		} catch (IOException e) {
			throw new ClientException(UNABLE_TO_WRITE_TEMP_FILE
					+ e.getMessage());
		}
	}

	/**
	 * Gets the data as an InputStream
	 * 
	 * @return the data
	 * 
	 * @throws ClientException
	 *             the client exception on I/O issues.
	 */
	public InputStream getDataStream() throws ClientException {
		if (storedFileName == null) {
			throw new ClientException(NO_DATA_FOUND);
		} else {
			File storedFile = new File(storedFileName);
			try {
				return new FileInputStream(storedFile);
			} catch (FileNotFoundException e) {
				throw new ClientException(CANNOT_REOPEN_TEMP_FILE
						+ storedFileName);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {
		File f = new File(storedFileName);
		f.deleteOnExit();
		try {
			super.finalize();
		} catch (Throwable t) {
		}
	}
}
