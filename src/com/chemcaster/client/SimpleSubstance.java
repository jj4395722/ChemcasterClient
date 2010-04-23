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
 * The Class SimpleSubstance.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleSubstance {

	Substance substance;

	/**
	 * Instantiates a new SimpleSubstance.
	 * 
	 * @param newSubstance
	 *            the new substance
	 */
	public SimpleSubstance(Substance newSubstance) {
		substance = newSubstance;
	}

	/**
	 * Instantiates a new SimpleSubstance from a URI.
	 * 
	 * @param substanceURI
	 *            the substance uri
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleSubstance(String substanceURI, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link subLink = Link.create(Substance.SUBSTANCE_MEDIA_TYPE,
				substanceURI, cred);
		substance = ClientHttp.get(subLink);
	}

	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = substance.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Gets the serialization.
	 * 
	 * @return the serialization
	 */
	public String getSerialization() {
		return substance.getSerialization();
	}

	/**
	 * Gets the inchi of the serialization.
	 * 
	 * @return the inchi
	 */
	public String getInchi() {
		return substance.getInchi();
	}

	/**
	 * Create a SimpleImage defined by the substance image index link.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param format
	 *            the format
	 * 
	 * @return the SimpleImage
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleImage getSimpleImage(Integer width, Integer height,
			String format) throws ClientException {
		return new SimpleImage(substance.getImagesLink(), width, height, format);
	}

	/**
	 * Gets the SimpleRegistration of this substance.
	 * 
	 * @return the SimpleRegistration
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistration getSimpleRegistration() throws ClientException {
		Registration r = ClientHttp.get(substance.getRegistrationLink());
		return new SimpleRegistration(r);
	}

	/**
	 * Gets the components of this substance.
	 * 
	 * @return the components
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getComponents() throws ClientException {
		Index componentsIndex = ClientHttp.get(substance.getComponentsLink());
		return componentsIndex.getItemNameURI();
	}

	/**
	 * Gets the SimpleComponent from the uri.
	 * 
	 * @param componentURI
	 *            the component uri
	 * 
	 * @return the SimpleComponent
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleComponent getSimpleComponentWithURI(String componentURI)
			throws ClientException {
		Link cLink = Link.create(Component.COMPONENT_MEDIA_TYPE, componentURI,
				substance.getLinkToSelf().getAuthentication());
		Component c = ClientHttp.get(cLink);
		return new SimpleComponent(c);
	}
	
	/**
	 * Gets the SimpleRegistry that owns this substance.
	 * 
	 * @return a SimpleRegistry instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistry() throws ClientException {
		Registry r = ClientHttp.get(substance.getRegistryLink());
		return new SimpleRegistry(r);
	}
}
