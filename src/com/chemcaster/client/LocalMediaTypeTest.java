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

import junit.framework.TestCase;

import org.junit.Test;

/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

public class LocalMediaTypeTest extends TestCase {
	private static final String GOOD_MEDIA_TYPE_STRING = "application/vnd.com.chemcaster.Service+json";
	private static final String BAD_MEDIA_TYPE_STRING = "application/vnd.com.chemcaster.BadRep+json";
	private static final String ZIP_MEDIA_TYPE_STRING = "application/zip";
	private static final String QUALIFIED_CLASS_NAME = "com.chemcaster.client.Service";
	private static final String SIMPLE_CLASS_NAME = "Service";

	public void testFullMediaClassName() {
		assertEquals(QUALIFIED_CLASS_NAME, LocalMediaType
				.qualifiedMediaClassName(GOOD_MEDIA_TYPE_STRING));
		assertEquals("com.chemcaster.client.Zip", LocalMediaType
				.qualifiedMediaClassName("application/zip"));
	}
	
	public void testSimpleMediaClassName() {
		assertEquals(SIMPLE_CLASS_NAME, LocalMediaType
				.simpleMediaClassName(GOOD_MEDIA_TYPE_STRING));
		assertEquals("Zip", LocalMediaType
				.simpleMediaClassName("application/zip"));
	}

	public void testGoodRepresentation() {
		Representation r = null;
		try {
		   r = LocalMediaType
				.createRepresentationInstance(GOOD_MEDIA_TYPE_STRING);
		} catch( ClientException clientException) {
			clientException.printStackTrace();
		}
		assertNotNull(r);
	}
	
	public void testZipRepresentation() {
		Representation r = null;
		try {
		   r = LocalMediaType
				.createRepresentationInstance(ZIP_MEDIA_TYPE_STRING);
		} catch( ClientException clientException) {
			clientException.printStackTrace();
		}
		assertNotNull(r);
	}	
	
	@Test(expected = ClientException.class)
	public void testBadRepresentation() {
		System.err.println("This test is supposed to throw a ClientException");
		try {
			LocalMediaType
				.createRepresentationInstance(BAD_MEDIA_TYPE_STRING);
		} catch( ClientException clientException) {
			clientException.printStackTrace();
		}
	}
	
	public void testHashKey() {
		String key =  LocalMediaType.generateHashKey(GOOD_MEDIA_TYPE_STRING);
		assertEquals(SIMPLE_CLASS_NAME.toLowerCase(), key);	
		String badKey =  LocalMediaType.generateHashKey(BAD_MEDIA_TYPE_STRING);
		assertNull(badKey);	
	}
}
