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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.TestCase;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONArray;
import org.json.JSONObject;

import com.chemcaster.client.Event;
import com.chemcaster.client.Link;
import com.chemcaster.client.Log;

public class MockLogTest extends TestCase {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	MockUtilities m = new MockUtilities();
	private Link aLink;
	private Log log;
	private JSONArray eventsJSON;
	private JSONObject attribJSON;
	private JSONObject logJSON;

	@Override
	protected void setUp() {
		aLink = mock(Link.class);
		UsernamePasswordCredentials cred = mock(UsernamePasswordCredentials.class);
		when(aLink.getAuthentication()).thenReturn(cred);

		eventsJSON = mock(JSONArray.class);
		attribJSON = mock(JSONObject.class);
		logJSON = mock(JSONObject.class);
		m.hasAndReturns(logJSON, "events", eventsJSON);
		m.hasAndReturns(logJSON, "log", attribJSON);
		log = new Log();
		log.populate(aLink, logJSON);
	}

	public void testLogDate() {
		m.hasAndReturns(attribJSON, "created_at", "2000-01-02 03:04:05 UTC");
		m.hasAndReturns(attribJSON, "interval", 600);
		Date created = log.getCreatedAt();
		assertNotNull(created);
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(created);
		assertEquals(c.get(Calendar.YEAR), 2000);
		assertEquals(c.get(Calendar.HOUR), 3);
		assertEquals(log.getInterval(), 600);
	}

	public void testEventsList() {
		JSONObject mockResource = mock(JSONObject.class);
		m.hasAndReturns(mockResource, "name", "x");
		m.hasAndReturnsString(mockResource, "uri", "https://a.org/b/1");
		m.hasAndReturnsString(mockResource, "media_type", "z");
		JSONObject mockEventJSON = mock(JSONObject.class);
		m.hasAndReturns(mockEventJSON, "logged_at", "2000-01-02 03:04:05 UTC");
		m.hasAndReturns(mockEventJSON, "resource", mockResource);

		try {
			when(eventsJSON.getJSONObject(0)).thenReturn(mockEventJSON);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		when(eventsJSON.length()).thenReturn(1);
		
		ArrayList<Event> results = log.getEventsList();
		assertEquals(results.size(),1);
		Event zero = results.get(0);
		assertNotNull(zero);
		Date logged = zero.getLoggedAt();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(logged);
		assertEquals(c.get(Calendar.YEAR), 2000);
		assertEquals(c.get(Calendar.HOUR), 3);
		
		try {
			Link r = zero.getResource();
			assertEquals(r.getURI(),"https://a.org/b/1");
			assertEquals(r.getMediaTypeString(),"z");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
