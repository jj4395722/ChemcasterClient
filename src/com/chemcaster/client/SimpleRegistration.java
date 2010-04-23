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

import java.util.ArrayList;

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * The Class SimpleRegistration, a wrapper around registration, with a
 * simplified interface.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleRegistration {
	Registration registration;

	/**
	 * Instantiates a new simple registration.
	 * 
	 * @param sr
	 *            the SimpleRegistry
	 * @param serialization
	 *            the serialization of a molecule, usually in molfile format
	 * @param multiplier
	 *            the multiplier for this molecule. Only 1.0 is valid at this
	 *            time in Chemcaster.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistration(SimpleRegistry sr, String serialization,
			Number multiplier) throws ClientException {
		AttributeHash finalAttribs = regAttributes(serialization, multiplier);

		Registry r = sr.getRegistry();
		Index regIndex = ClientHttp.get(r.getRegistrationsLink());
		registration = ClientHttp.post(regIndex.create(), finalAttribs);
	}

	/**
	 * Instantiates a new simple registration from a URI
	 * 
	 * @param registrationURI
	 *            the registration URI
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistration(String registrationURI, String username,
			String password) throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link rLink = Link.create(Registration.REGISTRATION_MEDIA_TYPE,
				registrationURI, cred);
		registration = ClientHttp.get(rLink);
	}

	/**
	 * Instantiates a new simple registration from a Registration instance.
	 * 
	 * @param r
	 *            the Registration instance
	 */
	public SimpleRegistration(Registration r) {
		registration = r;
	}

	/**
	 * Gets the registration.
	 * 
	 * @return the registration
	 */
	public Registration getRegistration() {
		return registration;
	}

	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = registration.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Gets the SimpleRegistry.
	 * 
	 * @return the SimpleRegistry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistry() throws ClientException {
		Registry r = ClientHttp.get(registration.getRegistryLink());
		return new SimpleRegistry(r);
	}

	/**
	 * Gets the serialization of a molecule, usually in molfile format.
	 * Currently returns the first molecule in the array returned. (As of this
	 * date, Chemcaster can only register single molecules).
	 * 
	 * @return the serialization
	 */
	public String getSerialization() {
		ArrayList<ComponentTemplate> template = registration.getTemplatesList();
		return template.get(0).getSerialization();
	}

	/**
	 * Gets the multiplier. Currently returns the first molecule in the array
	 * returned.
	 * 
	 * @return the multiplier
	 */
	public Number getMultiplier() {
		ArrayList<ComponentTemplate> template = registration.getTemplatesList();
		return template.get(0).getMultiplier();

	}

	/**
	 * Delete registration.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void deleteRegistration() throws ClientException {
		Registration delReg = ClientHttp.delete(registration.destroy());
		registration = delReg;
	}

	/**
	 * Update registration.
	 * 
	 * @param serialization
	 *            the serialization
	 * @param multiplier
	 *            the multiplier
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void updateRegistration(String serialization, Number multiplier)
			throws ClientException {
		AttributeHash updateAttribs = regAttributes(serialization, multiplier);
		registration = ClientHttp.put(registration.update(), updateAttribs);
	}

	/**
	 * Gets the Substance associated with successful completion of registration.
	 * 
	 * @return the Substance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Substance getSubstance() throws ClientException {
		return ClientHttp.get(registration.getSubstanceLink());
	}
	
	public NameURI getRegistrations() throws ClientException {
		Index registrationsIndex = ClientHttp.get(registration.getIndexLink());
		return registrationsIndex.getItemNameURI();
	}
	
	/**
	 * Gets the SimpleRegistration named by registrationURI.
	 * 
	 * @param registrationURI
	 *            the registration URI
	 * 
	 * @return the SimpleRegistration instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistration getSimpleRegistrationWithURI(String registrationURI)
			throws ClientException {
		Link rLink = Link.create(Registration.REGISTRATION_MEDIA_TYPE, registrationURI,
				registration.getLinkToSelf().getAuthentication());
		Registration r = ClientHttp.get(rLink);
		return new SimpleRegistration(r);
	}

	/**
	 * Set up the attributes needed for either a new registration or an update.
	 * 
	 * @param serialization
	 *            the serialization
	 * @param multiplier
	 *            the multiplier
	 * 
	 * @return the AttributeHash in the correct format for Registration
	 *         consumption.
	 */
	private AttributeHash regAttributes(String serialization, Number multiplier) {
		AttributeHash aTemplateItem = new AttributeHash();
		aTemplateItem.put("serialization", serialization);
		aTemplateItem.put("multiplier", multiplier);
		ArrayList<AttributeHash> templates = new ArrayList<AttributeHash>(1);
		templates.add(aTemplateItem);
		AttributeHash finalAttribs = new AttributeHash();
		finalAttribs.put("templates", templates);
		return finalAttribs;
	}
}
