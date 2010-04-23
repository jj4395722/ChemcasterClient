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

/**
 * The Class Registry. A Registry represents a collection of chemical structures
 * and resources for managing them. Each Registry defines rules that govern the
 * addition and removal of Substances and Structures. The media type is
 * [application/vnd.com.chemcaster.Registry+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Registry extends Item {
	public static final String REGISTRY_MEDIA_TYPE = "application/vnd.com.chemcaster.Registry+json";
	
	private static final String ARCHIVES_TAG = "archives";
	private static final String DELETABLE_TAG = "deletable";
	private static final String LOGS_TAG = "logs";
	private static final String NAME_TAG = "name";
	private static final String QUERIES_TAG = "queries";
	private static final String REGISTRATIONS_TAG = "registrations";
	private static final String SERVICE_TAG = "service";
	private static final String STRUCTURES_TAG = "structures";
	private static final String SUBSTANCES_TAG = "substances";

	private final String[] myAttributes = { NAME_TAG, DELETABLE_TAG };
	private final String[] myResources = { SERVICE_TAG, QUERIES_TAG,
			STRUCTURES_TAG, SUBSTANCES_TAG, ARCHIVES_TAG, REGISTRATIONS_TAG,
			LOGS_TAG, INDEX_TAG };

	/**
	 * Instantiates a new registry.
	 */
	public Registry() {
		super();
		addNewAttributeList(myAttributes);
		addNewResourceList(myResources);
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return getAttribute(NAME_TAG);
	}

	/**
	 * Checks if is deletable.
	 * 
	 * @return the boolean
	 */
	public Boolean isDeletable() {
		return getAttribute(DELETABLE_TAG);
	}

	/**
	 * Gets the service link.
	 * 
	 * @return the service link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getServiceLink() throws ClientException {
		return linkFromResourceBlock(SERVICE_TAG);
	}

	/**
	 * Gets the queries link.
	 * 
	 * @return the queries link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getQueriesLink() throws ClientException {
		return linkFromResourceBlock(QUERIES_TAG);
	}

	/**
	 * Gets the structures link.
	 * 
	 * @return the structures link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getStructuresLink() throws ClientException {
		return linkFromResourceBlock(STRUCTURES_TAG);
	}

	/**
	 * Gets the substances link.
	 * 
	 * @return the substances link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getSubstancesLink() throws ClientException {
		return linkFromResourceBlock(SUBSTANCES_TAG);
	}

	/**
	 * Gets the archives link.
	 * 
	 * @return the archives link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getArchivesLink() throws ClientException {
		return linkFromResourceBlock(ARCHIVES_TAG);
	}

	/**
	 * Gets the registrations link.
	 * 
	 * @return the registrations link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getRegistrationsLink() throws ClientException {
		return linkFromResourceBlock(REGISTRATIONS_TAG);
	}

	/**
	 * Gets the logs link.
	 * 
	 * @return the logs link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getLogsLink() throws ClientException {
		return linkFromResourceBlock(LOGS_TAG);
	}

}
