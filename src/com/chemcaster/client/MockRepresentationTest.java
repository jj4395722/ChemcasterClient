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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.test.MockUtilities;
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

public class MockRepresentationTest {
	public static final String INDEX_TAG = "index";

	public static final String ATTRIBUTE_TAG = "attribute";
	public static final String ATTRIBUTE_RESPONSE = "biffo";

	public static final String URI_TAG = "uri";
	public static final String URI_RESPONSE = "http://uri";

	public static final String MEDIA_TYPE_TAG = "media_type";
	public static final String MEDIA_TYPE_RESPONSE = "x";

	public static final String NAME_TAG = "name";
	public static final String NAME_RESPONSE = "someName";

	UsernamePasswordCredentials cred;
	Link someLink;
	JSONObject response;
	JSONObject genericLink;
	MockUtilities m = new MockUtilities();

	static class TestRepresentation extends Representation {
	}

	@Before
	public void setUp() throws Exception {
		cred = mock(UsernamePasswordCredentials.class);
		someLink = mock(Link.class);
		when(someLink.getAuthentication()).thenReturn(cred);

		JSONObject attributes = mock(JSONObject.class);

		try {
			m.hasAndReturns(attributes, ATTRIBUTE_TAG, ATTRIBUTE_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		genericLink = mock(JSONObject.class);

		try {
			m.hasAndReturnsString(genericLink, NAME_TAG, NAME_RESPONSE);
			m.hasAndReturnsString(genericLink, URI_TAG, URI_RESPONSE);
			m.hasAndReturnsString(genericLink, MEDIA_TYPE_TAG, MEDIA_TYPE_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response = mock(JSONObject.class);

		try {
			m.hasAndReturns(response, "mockrepresentationtest$testrepresentation", attributes);
			m.hasAndReturns(response, INDEX_TAG, genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testRepresentation() {
		TestRepresentation r = new TestRepresentation();
		assertNotNull(r);
	}

	// populate is tested in all other methods.
	// @Test
	// public final void testPopulate() {
	// TestRepresentation r = new TestRepresentation();
	// r.populate(someLink, response);
	// }

	@Test
	public final void testGetLinkToSelf() {
		TestRepresentation r = new TestRepresentation();
		r.populate(someLink, response);
		assertEquals(r.getLinkToSelf(), someLink);
	}

	@Test
	public final void testGetIndexLink() throws ClientException {
		TestRepresentation r = new TestRepresentation();
		r.addNewResource(INDEX_TAG);
		r.populate(someLink, response);
		Link link = r.getIndexLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testAddNewAttributeList() {
		String[] newAttributes = { ATTRIBUTE_TAG };
		TestRepresentation r = new TestRepresentation();
		r.addNewAttributeList(newAttributes);
		r.populate(someLink, response);
		assertEquals(r.getAttribute(ATTRIBUTE_TAG), ATTRIBUTE_RESPONSE);
	}

	@Test
	public final void testAddNewAttribute() {
		TestRepresentation r = new TestRepresentation();
		r.addNewAttribute(ATTRIBUTE_TAG);
		r.populate(someLink, response);
		assertEquals(r.getAttribute(ATTRIBUTE_TAG), ATTRIBUTE_RESPONSE);
	}

	@Test
	public final void testAddNewResourceList() throws ClientException  {
		String[] newResources = { INDEX_TAG };
		TestRepresentation r = new TestRepresentation();
		r.addNewResourceList(newResources);
		r.populate(someLink, response);
		Link link = r.getIndexLink();
		assertNotNull(link);
	}

	@Test
	public final void testAddNewResource() throws ClientException {
		TestRepresentation r = new TestRepresentation();
		r.addNewResource(INDEX_TAG);
		r.populate(someLink, response);
		Link link = r.getIndexLink();
		assertNotNull(link);
	}

	@Test
	public final void testLinkFromResourceBlock() throws ClientException {
		TestRepresentation r = new TestRepresentation();
		r.addNewResource(INDEX_TAG);
		r.populate(someLink, response);
		Link link = r.getIndexLink();
		assertNotNull(link);
	}

	@Test
	public final void testGetAttribute()throws ClientException {
		TestRepresentation r = new TestRepresentation();
		r.addNewAttribute(ATTRIBUTE_TAG);
		r.populate(someLink, response);
		assertEquals(r.getAttribute(ATTRIBUTE_TAG), ATTRIBUTE_RESPONSE);
	}

	@Test
	public final void testGetResource() throws ClientException{
		TestRepresentation r = new TestRepresentation();
		r.addNewResource(INDEX_TAG);
		r.populate(someLink, response);
		Link link = r.getIndexLink();
		assertNotNull(link);
	}

	@Test
	public final void testGetObjectFromJSON() {
		TestRepresentation r = new TestRepresentation();
		HashSet<String> goodKeys = new HashSet<String>();
		goodKeys.add( INDEX_TAG );
		JSONObject index = (JSONObject) r.getObjectFromJSON(INDEX_TAG, response, goodKeys );
		assertEquals(index.toString(), genericLink.toString());
	}

	@Test
	public final void testUncheckedGetObjectFromJSON() {
		TestRepresentation r = new TestRepresentation();
		JSONObject index = (JSONObject) r.uncheckedGetObjectFromJSON(INDEX_TAG, response );
		assertEquals(index.toString(), genericLink.toString());
	}

	@Test
	public final void testSimpleClassName() {
		TestRepresentation r = new TestRepresentation();
		assertEquals(r.simpleClassName(),"MockRepresentationTest$TestRepresentation");
	}

	@Test
	public final void testQualifiedClassName() {
		TestRepresentation r = new TestRepresentation();
		assertEquals(r.qualifiedClassName(),"com.chemcaster.client.MockRepresentationTest$TestRepresentation");
	}

	@Test
	public final void testResourceString() {
		TestRepresentation r = new TestRepresentation();
		String insert = "abc";
		assertEquals(r.resourceString(insert),"application/vnd.com.chemcaster.abc+json");
	}

}
