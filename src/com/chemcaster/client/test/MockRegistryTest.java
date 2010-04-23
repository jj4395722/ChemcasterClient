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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.Link;
import com.chemcaster.client.Registry;

public class MockRegistryTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	public final static String DELETABLE = "deletable";
	public static final Boolean DELETABLE_RESPONSE = true;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cred = mock(UsernamePasswordCredentials.class);
		someLink = mock(Link.class);
		when(someLink.getAuthentication()).thenReturn(cred);
		response = mock(JSONObject.class);

		JSONObject registryResponse = mock(JSONObject.class);

		try {
			m.hasAndReturns(registryResponse, NAME, NAME_RESPONSE);
			m.hasAndReturns(registryResponse, DELETABLE, DELETABLE_RESPONSE);
			m.hasAndReturns(response, "registry", registryResponse);
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

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#Registry()}.
	 */
	@Test
	public final void testRegistry() {
		Registry r = new Registry();
		assertNotNull(r);
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#getName()}.
	 */
	@Test
	public final void testGetName() {
		Registry r = new Registry();
		r.populate(someLink, response);
		assertEquals(NAME_RESPONSE, r.getName());
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#isDeletable()}.
	 */
	@Test
	public final void testIsDeletable() {
		Registry r = new Registry();
		r.populate(someLink, response);
		assertTrue(r.isDeletable());
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#getServiceLink()}.
	 */
	@Test
	public final void testGetServiceLink() throws ClientException {

		try {
			m.hasAndReturns(response, "service", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getServiceLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#getQueriesLink()}.
	 */
	@Test
	public final void testGetQueriesLink() throws ClientException {

		try {
			m.hasAndReturns(response, "queries", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getQueriesLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for
	 * {@link com.chemcaster.client.Registry#getStructuresLink()}.
	 */
	@Test
	public final void testGetStructuresLink() throws ClientException {
		try {
			m.hasAndReturns(response, "structures", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getStructuresLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for
	 * {@link com.chemcaster.client.Registry#getSubstancesLink()}.
	 */
	@Test
	public final void testGetSubstancesLink() throws ClientException {
		try {
			m.hasAndReturns(response, "substances", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getSubstancesLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#getArchivesLink()}.
	 */
	@Test
	public final void testGetArchivesLink() throws ClientException {
		try {
			m.hasAndReturns(response, "archives", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getArchivesLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for
	 * {@link com.chemcaster.client.Registry#getRegistrationsLink()}.
	 */
	@Test
	public final void testGetRegistrationsLink() throws ClientException {
		try {
			m.hasAndReturns(response, "registrations", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getRegistrationsLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	/**
	 * Test method for {@link com.chemcaster.client.Registry#getLogsLink()}.
	 */
	@Test
	public final void testGetLogsLink() throws ClientException {
		try {
			m.hasAndReturns(response, "logs", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry r = new Registry();
		r.populate(someLink, response);
		Link link = r.getLogsLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}
}
