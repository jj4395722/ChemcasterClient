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

import java.util.ArrayList;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.Execution;
import com.chemcaster.client.Link;
import com.chemcaster.client.NameURI;

public class MockExecutionTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

	final String REVERSE = "reverse";
	final Boolean REVERSE_RESPONSE = true;

	final String CURSOR = "cursor";
	final String CURSOR_RESPONSE = "136";

	final String NEXT_CURSOR = "next_cursor";
	final String NEXT_CURSOR_RESPONSE = "135";

	final String MAXIMUM_RESULTS = "maximum_results";
	final Number MAXIMUM_RESULTS_RESPONSE = 5;

	final String PREVIOUS_CURSOR = "previous_cursor";
	final String PREVIOUS_CURSOR_RESPONSE = "130";

	final String URI = "uri";
	final String URI_RESPONSE = "http://uri";

	final String MEDIA_TYPE = "media_type";
	final String MEDIA_TYPE_RESPONSE = "x";

	final String NAME = "name";
	final String NAME_RESPONSE = "someName";

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
			m.hasAndReturns(registrationResponse, REVERSE, REVERSE_RESPONSE);
			m.hasAndReturns(registrationResponse, CURSOR, CURSOR_RESPONSE);
			m.hasAndReturns(registrationResponse, NEXT_CURSOR,
					NEXT_CURSOR_RESPONSE);
			m.hasAndReturns(registrationResponse, MAXIMUM_RESULTS,
					MAXIMUM_RESULTS_RESPONSE);
			m.hasAndReturns(registrationResponse, PREVIOUS_CURSOR,
					PREVIOUS_CURSOR_RESPONSE);
			m.hasAndReturns(response, "execution", registrationResponse);
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
	public final void testExecution() {
		Execution e = new Execution();
		assertNotNull(e);
	}

	@Test
	public final void testGetCursor() {
		Execution e = new Execution();
		e.populate(someLink, response);
		assertEquals(e.getCursor(), CURSOR_RESPONSE);
	}

	@Test
	public final void testGetMaximumResults() {
		Execution e = new Execution();
		e.populate(someLink, response);
		assertEquals(e.getMaximumResults(), MAXIMUM_RESULTS_RESPONSE);
	}

	@Test
	public final void testIsReverse() {
		Execution e = new Execution();
		e.populate(someLink, response);
		assertEquals(e.isReverse(), REVERSE_RESPONSE);
	}

	@Test
	public final void testGetNextCursor() {
		Execution e = new Execution();
		e.populate(someLink, response);
		assertEquals(e.getNextCursor(), NEXT_CURSOR_RESPONSE);
	}

	@Test
	public final void testGetPreviousCursor() {
		Execution e = new Execution();
		e.populate(someLink, response);
		assertEquals(e.getPreviousCursor(), PREVIOUS_CURSOR_RESPONSE);
	}

	@Test
	public final void testGetExecutableLink() throws ClientException {
		try {
			m.hasAndReturns(response, "executable", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Execution e = new Execution();
		e.populate(someLink, response);
		Link link = e.getExecutableLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetSubstancesLinks() throws ClientException {

		JSONArray innerArray = mock(JSONArray.class);
		when(innerArray.length()).thenReturn(1);

		JSONArray outerArray = mock(JSONArray.class);
		when(outerArray.length()).thenReturn(1);

		try {
			when(innerArray.getJSONObject(0)).thenReturn(genericLink);

			when(outerArray.getJSONArray(0)).thenReturn(innerArray);

			m.hasAndReturns(response, "substances", outerArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Execution e = new Execution();
		e.populate(someLink, response);
		ArrayList<ArrayList<Link>> subLinks = e.getSubstancesLinks();
		Link link = subLinks.get(0).get(0);
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

	@Test
	public final void testGetSubstances() throws ClientException {

		JSONArray innerArray = mock(JSONArray.class);
		when(innerArray.length()).thenReturn(1);

		JSONArray outerArray = mock(JSONArray.class);
		when(outerArray.length()).thenReturn(1);

		try {
			when(innerArray.getJSONObject(0)).thenReturn(genericLink);

			when(outerArray.getJSONArray(0)).thenReturn(innerArray);

			m.hasAndReturns(response, "substances", outerArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Execution e = new Execution();
		e.populate(someLink, response);
		NameURI nameURIs = e.getSubstances();
		assertEquals(nameURIs.size(), 1);
		assertEquals(nameURIs.getURI(0), URI_RESPONSE);
	}

}
