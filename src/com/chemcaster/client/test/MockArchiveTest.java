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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.Archive;
import com.chemcaster.client.ClientException;
import com.chemcaster.client.Link;


public class MockArchiveTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

	public final static String DONE = "done";
	public static final Boolean DONE_RESPONSE = true;

	public final static String CREATED_AT = "created_at";
	public final static String CREATED_AT_RESPONSE = "Fri, 04 Sep 2009 01:12:09 +0000";
	public final static String CHEMCASTER_ARCHIVE_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";

	public final static String URI = "uri";
	public final static String URI_RESPONSE = "http://uri";

	public final static String MEDIA_TYPE = "media_type";
	public final static String MEDIA_TYPE_RESPONSE = "x";

	public final static String NAME = "name";
	public final static String NAME_RESPONSE = "someName";

	UsernamePasswordCredentials cred;
	Link someLink;
	JSONObject response;
	JSONObject genericLink;
	MockUtilities m = new MockUtilities();

	@Before
	public void setUp() throws Exception {
		cred = mock(UsernamePasswordCredentials.class);
		someLink = mock(Link.class);
		when(someLink.getAuthentication()).thenReturn(cred);
		response = mock(JSONObject.class);

		JSONObject arrayItem = mock(JSONObject.class);
		JSONArray templateResponse = mock(JSONArray.class);
		when(templateResponse.length()).thenReturn(1);
		when(templateResponse.getJSONObject(0)).thenReturn(arrayItem);

		JSONObject registrationResponse = mock(JSONObject.class);

		try {
			m.hasAndReturns(registrationResponse, DONE, DONE_RESPONSE);
			m.hasAndReturns(registrationResponse, CREATED_AT, CREATED_AT_RESPONSE);
			m.hasAndReturns(response, "archive", registrationResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		genericLink = mock(JSONObject.class);

		try {
			m.hasAndReturnsString(genericLink, NAME, NAME_RESPONSE);
			m.hasAndReturnsString(genericLink, URI, URI_RESPONSE);
			m.hasAndReturnsString(genericLink, MEDIA_TYPE, MEDIA_TYPE_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testArchive() {
		Archive a = new Archive();
		assertNotNull(a);
	}

	@Test
	public final void testGetCreatedAt() {
		SimpleDateFormat df = new SimpleDateFormat(CHEMCASTER_ARCHIVE_DATE_FORMAT);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Archive a = new Archive();
		a.populate(someLink, response);
		
		Date createdDate = a.getCreatedAt();
		assertEquals(df.format(createdDate), CREATED_AT_RESPONSE);
	}

	@Test
	public final void testIsDone() {
		Archive a = new Archive();
		a.populate(someLink, response);
		assertEquals(a.isDone(),DONE_RESPONSE);
	}

	@Test
	public final void testDestroy() throws ClientException {
		try {
			m.hasAndReturns(response, "destroy", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Archive a = new Archive();
		a.populate(someLink, response);
		Link link = a.getDestroyLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetZipfileLink() throws ClientException  {
		try {
			m.hasAndReturns(response, "zipfile", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Archive a = new Archive();
		a.populate(someLink, response);
		Link link = a.getZipfileLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetRegistryLink() throws ClientException  {
		try {
			m.hasAndReturns(response, "registry", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Archive a = new Archive();
		a.populate(someLink, response);
		Link link = a.getRegistryLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

}
