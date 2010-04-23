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

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * The Class Service.
 * 
 * A Service represents the top-level administrative interface to a Chemcaster
 * account. The media type is [application/vnd.com.chemcaster.Service+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Service extends Representation {
	private static final String SERVICE_MEDIA_TYPE_STRING = "application/vnd.com.chemcaster.Service+json";
	private static final String REGISTRIES = "registries";
	private static final String VERSION = "version";
	protected static final String DEFAULT_URI = "https://chemcaster.com/rest";

	public static Link getServiceLink(UsernamePasswordCredentials userId) throws ClientException {
		return getServiceLink(DEFAULT_URI, userId);
	}

	public static Link getServiceLink(String userURI, UsernamePasswordCredentials userId)
			throws ClientException {
        String uri;
		if (userURI == null || userURI.isEmpty()) {
			uri = DEFAULT_URI;
		} else {
			uri = userURI;
		}
		return Link.create(SERVICE_MEDIA_TYPE_STRING, uri, userId);
	}

	/**
	 * Instantiates a new service.
	 */
	public Service() {
		super();
		addNewAttribute(VERSION);
		addNewResource(REGISTRIES);
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return getAttribute(VERSION);
	}

	/**
	 * Gets the registries.
	 * 
	 * @return the link to the registries
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getRegistriesLink() throws ClientException {
		return linkFromResourceBlock(REGISTRIES);
	}
	
	/**
	 * Returns null, as there is no Index resource for Service
	 */
	public Link getIndexLink() {
		return null;
	}
}
