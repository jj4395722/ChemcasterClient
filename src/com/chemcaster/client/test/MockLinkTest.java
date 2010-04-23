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
import junit.framework.TestCase;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;

import com.chemcaster.client.Link;

public class MockLinkTest extends TestCase {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	private UsernamePasswordCredentials id;
	private Link aLink;
	MockUtilities m = new MockUtilities();

	@Override
	protected void setUp() {
		id = mock(UsernamePasswordCredentials.class);
	}

	public void testLinkCreate3Params() {
		aLink = Link.create("someMediaType", "someURI", id);
		assertNotNull(aLink);
	}

	public void testGoodLinkCreateFromJSON() {
		JSONObject jo = mock(JSONObject.class);

		aLink = Link.create(jo, id);

		assertNotNull(aLink);
	}

	public void testLinkCreateFromNullJSON() {
		// this should work, but aLink should be null on return.
		// there are cases when this routine gets fed null.
		assertNull(Link.create(null, id));
	}

	public void testGetURIHost() {
		JSONObject jo = mock(JSONObject.class);
		m.hasAndReturnsString(jo, "uri", "https://a.org/b/1");
		m.hasAndReturnsString(jo, "media_type", "x");
		m.hasAndReturnsString(jo, "name", "name");

		aLink = Link.create(jo, id);
		assertNotNull(aLink);
		assertEquals("https://a.org/b/1", aLink.getURI());
		assertEquals("a.org", aLink.getURIHost());
		assertEquals("name", aLink.getName());
		assertEquals("x", aLink.getMediaTypeString());
	}
}
