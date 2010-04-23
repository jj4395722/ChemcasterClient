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

import junit.framework.TestCase;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONArray;
import org.json.JSONObject;

import com.chemcaster.client.Index;
import com.chemcaster.client.Link;

public class MockIndexTest extends TestCase {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	MockUtilities m = new MockUtilities();
	private Link aLink;
	private Index index;
	private JSONArray itemsJSON;
	private JSONObject indexJSON;

	@Override
	protected void setUp() {
		aLink = mock(Link.class);
		UsernamePasswordCredentials cred = mock(UsernamePasswordCredentials.class);
		when(aLink.getAuthentication()).thenReturn(cred);
		
		itemsJSON = mock(JSONArray.class);
		indexJSON = mock(JSONObject.class);
		m.hasAndReturns(indexJSON, "items", itemsJSON);
		index = new Index();
		index.populate(aLink, indexJSON);
	}

	public void testZeroItems() {
		try {
			when(itemsJSON.length()).thenReturn(0);
			ArrayList<Link> result = index.getItemLinksList();
			assertNotNull(result);
			assertEquals(result.size(), 0);
		} catch (Exception e) {
			System.out.println("json error: " + e);
		}
	}

	public void testWithItems() {
		JSONObject mockResource = mock(JSONObject.class);
		m.hasAndReturnsString(mockResource, "uri", "https://a.org/b/1");
		m.hasAndReturnsString(mockResource, "media_type", "x");
		
		try {
			when(itemsJSON.getJSONObject(0)).thenReturn(mockResource);
			when(itemsJSON.getJSONObject(1)).thenReturn(mockResource);
			when(itemsJSON.length()).thenReturn(2);
			ArrayList<Link> result = index.getItemLinksList();
			assertNotNull(result);
			assertEquals(result.size(), 2);
			Link zero = result.get(0);
			assertNotNull(zero);
			assertEquals(zero.getURI(),"https://a.org/b/1");
			assertEquals(zero.getMediaTypeString(),"x");
			Link one = result.get(0);
			assertNotNull(one);
			assertEquals(one.getURI(),"https://a.org/b/1");
			assertEquals(one.getMediaTypeString(),"x");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
