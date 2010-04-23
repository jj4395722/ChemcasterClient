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
 * The Class SimpleRegistry is a simplified API on Registry
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleRegistry {
	Registry registry;

	/**
	 * Instantiates a new SimpleRegistry based on an existing Registry instance.
	 * 
	 * @param newRegistry
	 *            the new registry
	 */
	public SimpleRegistry(Registry newRegistry) {
		registry = newRegistry;
	}

	/**
	 * Instantiates a new SimpleRegistry of an existing registry.
	 * 
	 * @param registryURI
	 *            the existing registry's uri
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry(String registryURI, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link rLink = Link.create(Registry.REGISTRY_MEDIA_TYPE, registryURI,
				cred);
		registry = ClientHttp.get(rLink);
	}

	/**
	 * Create a new Registry and instantiates a new SimpleRegistry for it.
	 * 
	 * @param simpleService
	 *            a SimpleService instance
	 * @param name
	 *            the name of the new registry
	 * @param deletable
	 *            the deletable state of the registry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry(SimpleService simpleService, String name,
			Boolean deletable) throws ClientException {
		AttributeHash newRegAttribs = new AttributeHash();
		newRegAttribs.put("name", name);
		newRegAttribs.put("deletable", deletable);

		Service s = simpleService.getService();
		Index regIndex = ClientHttp.get(s.getRegistriesLink());
		registry = ClientHttp.post(regIndex.create(), newRegAttribs);
	}

	/**
	 * Gets the registry.
	 * 
	 * @return the registry
	 */
	public Registry getRegistry() {
		return registry;
	}

	/**
	 * Checks if is deletable.
	 * 
	 * @return the boolean
	 */
	public Boolean isDeletable() {
		return registry.isDeletable();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return registry.getName();
	}

	/**
	 * Gets the URI for this resource.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = registry.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Sets the deletable status of the registry represented by this instance.
	 * 
	 * @param deletable
	 *            the deletable
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void setDeletable(Boolean deletable) throws ClientException {
		updateRegistry(registry.getName(), deletable);
	}

	/**
	 * Sets a new name of the registry represented by this instance.
	 * 
	 * @param newName
	 *            the new name
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void setName(String newName) throws ClientException {
		updateRegistry(newName, registry.isDeletable());
	}

	/**
	 * Update registry represented by this instance.
	 * 
	 * @param name
	 *            the name
	 * @param deletable
	 *            the deletable status of the registry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	private void updateRegistry(String name, Boolean deletable)
			throws ClientException {
		AttributeHash updatedAttribs = new AttributeHash();
		updatedAttribs.put("name", name);
		updatedAttribs.put("deletable", deletable);
		registry = ClientHttp.put(registry.update(), updatedAttribs);
	}

	/**
	 * Delete the registry represented by this instance.
	 * 
	 * @throws ClientException
	 */

	public void deleteRegistry() throws ClientException {
		setDeletable(true);
		Registry delReg = ClientHttp.delete(registry.destroy());
		registry = delReg;
	}

	/**
	 * Gets the SimpleService that owns this SimpleRegistry.
	 * 
	 * @return a SimpleService instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleService getSimpleService() throws ClientException {
		Service s = ClientHttp.get(registry.getServiceLink());
		return new SimpleService(s);
	}

	/**
	 * Gets the names and URIs of the queries.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getQueries() throws ClientException {
		Index queriesIndex = ClientHttp.get(registry.getQueriesLink());
		return queriesIndex.getItemNameURI();
	}

	/**
	 * Gets the query identified by queryURI.
	 * 
	 * @param queryURI
	 *            the query URI
	 * 
	 * @return the Query instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleQuery getSimpleQueryWithURI(String queryURI)
			throws ClientException {
		Link qLink = Link.create(Query.QUERY_MEDIA_TYPE, queryURI, registry
				.getLinkToSelf().getAuthentication());
		Query q = ClientHttp.get(qLink);
		return new SimpleQuery(q);
	}

	/**
	 * Gets the names and URIs of the structures.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getStructures() throws ClientException {
		Index structuresIndex = ClientHttp.get(registry.getStructuresLink());
		return structuresIndex.getItemNameURI();
	}

	/**
	 * Gets the structure identified by structureURI
	 * 
	 * @param structureURI
	 *            the structure URI
	 * 
	 * @return the structure
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleStructure getSimpleStructureWithURI(String structureURI)
			throws ClientException {
		Link sLink = Link.create(Structure.STRUCTURE_MEDIA_TYPE, structureURI,
				registry.getLinkToSelf().getAuthentication());
		Structure s = ClientHttp.get(sLink);
		return new SimpleStructure(s);
	}

	/**
	 * Gets the names and URIs of the substances.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getSubstances() throws ClientException {
		Index substancesIndex = ClientHttp.get(registry.getSubstancesLink());
		return substancesIndex.getItemNameURI();
	}

	/**
	 * Gets the SimpleSubstance identified by substanceURI.
	 * 
	 * @param substanceURI
	 *            the substance URI
	 * 
	 * @return the SimpleSubstance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleSubstance getSimpleSubstanceWithURI(String substanceURI)
			throws ClientException {
		Link sLink = Link.create(Substance.SUBSTANCE_MEDIA_TYPE, substanceURI,
				registry.getLinkToSelf().getAuthentication());
		Substance s = ClientHttp.get(sLink);
		return new SimpleSubstance(s);
	}

	/**
	 * Gets the names and URIs of the registrations.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getRegistrations() throws ClientException {
		Index registrationsIndex = ClientHttp.get(registry
				.getRegistrationsLink());
		return registrationsIndex.getItemNameURI();
	}

	/**
	 * Gets the SimpleRegistration identified by registration URI
	 * 
	 * @param registrationURI
	 *            the registration URI
	 * 
	 * @return the SimpleRegistration instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistration getSimpleRegistrationWithURI(
			String registrationURI) throws ClientException {
		Link rLink = Link.create(Registration.REGISTRATION_MEDIA_TYPE,
				registrationURI, registry.getLinkToSelf().getAuthentication());
		Registration r = ClientHttp.get(rLink);
		return new SimpleRegistration(r);
	}

	/**
	 * Gets the names and URIs of the registries.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getRegistries() throws ClientException {
		Index registriesIndex = ClientHttp.get(registry.getIndexLink());
		return registriesIndex.getItemNameURI();
	}

	/**
	 * Gets the registry named by registryName.
	 * 
	 * @param registryName
	 *            the registry name
	 * 
	 * @return the registry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistryWithName(String registryName)
			throws ClientException {
		Index registriesIndex = ClientHttp.get(registry.getIndexLink());
		Registry r = ClientHttp.get(registriesIndex.getItemNamed(registryName));
		return new SimpleRegistry(r);
	}

	/**
	 * Gets the names and URIs of the logs.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getLogs() throws ClientException {
		Index logsIndex = ClientHttp.get(registry.getLogsLink());
		return logsIndex.getItemNameURI();
	}

	/**
	 * Gets the SimpleLog with the URI logURI
	 * 
	 * @param logURI
	 *            the log URI
	 * 
	 * @return the SimpleLog
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleLog getSimpleLogWithURI(String logURI) throws ClientException {
		Link logLink = Link.create(Log.LOG_MEDIA_TYPE, logURI, registry
				.getLinkToSelf().getAuthentication());
		Log aLog = ClientHttp.get(logLink);
		return new SimpleLog(aLog);
	}
	
	/**
	 * Create a new SimpleArchive of this registry.
	 * 
	 * @return a SimpleArchive
	 * 
	 * @throws ClientException
	 */
	public SimpleArchive getSimpleArchive() throws ClientException {
		Index archiveIndex = ClientHttp.get(registry.getArchivesLink());
		Archive arc = ClientHttp.post(archiveIndex.create(),
				new AttributeHash());
		return new SimpleArchive(arc);
	}

}
