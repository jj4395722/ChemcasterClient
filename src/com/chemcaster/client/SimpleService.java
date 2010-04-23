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
 * The Class SimpleService is a simplified API on Service
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleService {

	Service service;

	/**
	 * Instantiates a new SimpleService with the default url.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleService(String username, String password)
			throws ClientException {
		service = createService(Service.DEFAULT_URI, username, password);
	}

	/**
	 * Instantiates a new SimpleService with a user supplied url
	 * 
	 * @param userURI
	 *            the user uri
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleService(String userURI, String username, String password)
			throws ClientException {
		service = createService(userURI, username, password);
	}

	/**
	 * Create a new Service instance from a supplied URI, userid, password
	 * 
	 * @param userURI
	 *            the user uri
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	private Service createService(String userURI, String username,
			String password) throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		return ClientHttp.get(Service.getServiceLink(userURI, cred));

	}

	/**
	 * Instantiates a new SimpleService with a user supplied Service instance
	 * 
	 * @param newService
	 *            the user supplied service instance
	 */
	public SimpleService(Service newService) {
		service = newService;
	}

	/**
	 * Gets the base Service instance.
	 * 
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Gets the version of the registry.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return service.getVersion();
	}

	/**
	 * Gets the registry identifiers as a NameURI.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getRegistries() throws ClientException {
		Index registriesIndex = ClientHttp.get(service.getRegistriesLink());
		return registriesIndex.getItemNameURI();
	}

	/**
	 * Gets the registry named by registryName.
	 * 
	 * @param registryName
	 *            the registry name
	 * 
	 * @return the Registry instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistryWithName(String registryName) throws ClientException {
		Index registriesIndex = ClientHttp.get(service.getRegistriesLink());
		Registry r = ClientHttp.get(registriesIndex.getItemNamed(registryName));
		return new SimpleRegistry(r);
	}
	
	/**
	 * Gets the URI for this resource.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = service.getLinkToSelf();
		return l.getURI();
	}
}
