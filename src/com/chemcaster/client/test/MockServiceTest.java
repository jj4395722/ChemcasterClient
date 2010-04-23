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

import com.chemcaster.client.ClientException;
import com.chemcaster.client.Link;
import com.chemcaster.client.Service;

public class MockServiceTest extends TestCase {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
	private static final String SERVICE = "service";

	UsernamePasswordCredentials cred;
	private JSONObject response;
	MockUtilities m = new MockUtilities();

	@Override
	protected void setUp() {
		cred = mock(UsernamePasswordCredentials.class);
		response = mock(JSONObject.class);
	}

	public void testGetServiceLinkOneArg() throws ClientException {
		Link serviceLink = Service.getServiceLink(cred);
		assertNotNull(serviceLink);
	}

	public void testGetServiceLinkTwoArgs() throws ClientException {
		Link serviceLink2 = Service.getServiceLink("url", cred);
		assertNotNull(serviceLink2);
	}

	public void testServiceVersion() throws ClientException {
		final String VERSION = "version";
		final String VERSION_RESPONSE = "0.0.1";

		JSONObject attributeResponse = mock(JSONObject.class);

		try {
			m.hasAndReturns(attributeResponse, VERSION, VERSION_RESPONSE);
			m.hasAndReturns(response, SERVICE, attributeResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Service s = new Service();
		s.populate(mock(Link.class), response);
		assertEquals(VERSION_RESPONSE, s.getVersion());
	}

	public void testRegistries() throws ClientException {
		final String URI = "uri";
		final String URI_RESPONSE = "http://a.b/c";
		final String MEDIA_TYPE = "media_type";
		final String MEDIA_TYPE_RESPONSE = "m";
		final String NAME = "name";
		final String NAME_RESPONSE = "aName";

		JSONObject registriesResponse = mock(JSONObject.class);

		try {
			m.hasAndReturnsString(registriesResponse, URI, URI_RESPONSE);
			m.hasAndReturnsString(registriesResponse, MEDIA_TYPE,
					MEDIA_TYPE_RESPONSE);
			m.hasAndReturnsString(registriesResponse, NAME, NAME_RESPONSE);
			m.hasAndReturns(response, "registries", registriesResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Service s = new Service();
		s.populate(mock(Link.class), response);
		Link l = s.getRegistriesLink();
		assertNotNull(l);
		assertEquals(l.getURI(), URI_RESPONSE);
		assertEquals(l.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(l.getName(), NAME_RESPONSE);
	}
}
