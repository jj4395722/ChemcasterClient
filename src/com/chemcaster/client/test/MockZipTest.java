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
package com.chemcaster.client.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

import junit.framework.TestCase;

import com.chemcaster.client.Zip;

public class MockZipTest extends TestCase {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	private static final String ARCHIVE_FILE_PREFIX = "CCArch";

	public void testSetData() {
		// 40
		String demo = "<<abcdefghijklmnopqrstuvwxyz0123456789>>";
		// 400
		String lDemo = demo + demo + demo + demo + demo + demo
				+ demo + demo + demo + demo;
		// 4000
		String finalDemo = lDemo + lDemo + lDemo + lDemo + lDemo
				+ lDemo + lDemo + lDemo + lDemo + lDemo;
		try {
			InputStream is = new ByteArrayInputStream(finalDemo
					.getBytes("UTF-8"));
			int preCount = CCFileTempCount();
			Zip z = new Zip();
			z.setData(is);

			Thread.sleep(100L);
			int postCount = CCFileTempCount();
			assertEquals(preCount + 1, postCount);

			// due to unpredictability of when System.gc() happens, and when
			// Zip's finalize runs, file destruction cannot be tested.
			// In practice, the file does disappear after this test is run.

			z = null;
			// System.gc();
			// Thread.sleep(100L);
			// int gcCount = CCFileTempCount();
			// assertEquals(preCount, gcCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void testGetData() {
	// }

	private int CCFileTempCount() {
		File dir = new File(System.getProperty("java.io.tmpdir"));
		FilenameFilter ccfileFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(ARCHIVE_FILE_PREFIX);
			}
		};
		return dir.list(ccfileFilter).length;
	}

}
