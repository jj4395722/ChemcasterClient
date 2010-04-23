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

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.Link;
import com.chemcaster.client.Query;

public class MockQueryTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

	public final static String SERIALIZATION = "serialization";
	public final static String SERIALIZATION_RESPONSE = "molfile";

	public final static String MODE = "mode";
	public final static String MODE_RESPONSE = "Exact";

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

		JSONObject structureResponse = mock(JSONObject.class);

		try {
			m.hasAndReturns(structureResponse, SERIALIZATION,
					SERIALIZATION_RESPONSE);
			m.hasAndReturns(structureResponse, MODE, MODE_RESPONSE);
			m.hasAndReturns(response, "query", structureResponse);
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
	public final void testQuery() {
		Query q = new Query();
		assertNotNull(q);
	}

	@Test
	public final void testGetSerialization() {
		Query q = new Query();
		q.populate(someLink, response);
		assertEquals(q.getSerialization(), SERIALIZATION_RESPONSE);
	}

	@Test
	public final void testGetMode() {
		Query q = new Query();
		q.populate(someLink, response);
		assertEquals(q.getMode(), MODE_RESPONSE);
	}

	@Test
	public final void testGetImagesLink() throws ClientException {
		try {
			m.hasAndReturns(response, "images", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query q = new Query();
		q.populate(someLink, response);
		Link link = q.getImagesLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetRegistryLink() throws ClientException {
		try {
			m.hasAndReturns(response, "registry", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query q = new Query();
		q.populate(someLink, response);
		Link link = q.getRegistryLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetExecutionsLink() throws ClientException {
		try {
			m.hasAndReturns(response, "executions", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query q = new Query();
		q.populate(someLink, response);
		Link link = q.getExecutionsLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

}
