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
 * The Class Structure. A Structure represents a chemical structure. A Structure
 * is associated with at least one Substance through a Component. Structures can
 * only be created by creating Substances. All attributes are read-only. The
 * media type is [application/vnd.com.chemcaster.Structure+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Structure extends Item {
	public static final String STRUCTURE_MEDIA_TYPE = "application/vnd.com.chemcaster.Structure+json";;

	private static final String COMPONENTS_TAG = "components";
	private static final String IMAGES_TAG = "images";
	private static final String INCHI_TAG = "inchi";
	private static final String REGISTRY_TAG = "registry";
	private static final String SERIALIZATION_TAG = "serialization";

	private String[] NEW_ATTRIBUTES = { SERIALIZATION_TAG, INCHI_TAG };
	private String[] NEW_RESOURCES = { IMAGES_TAG, REGISTRY_TAG, COMPONENTS_TAG };

	/**
	 * Instantiates a new structure.
	 */
	public Structure() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the serializated version of the Structure, typically in molfile
	 * format.
	 * 
	 * @return the serialization
	 */
	public String getSerialization() {
		return getAttribute(SERIALIZATION_TAG);
	}

	/**
	 * Gets the InChi version of the Structure.
	 * 
	 * @return the InChi string
	 */
	public String getInchi() {
		return getAttribute(INCHI_TAG);
	}

	/**
	 * Gets the images link.
	 * 
	 * @return the images link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getImagesLink() throws ClientException {
		return linkFromResourceBlock(IMAGES_TAG);
	}

	/**
	 * Gets the registry link.
	 * 
	 * @return the registry link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getRegistryLink() throws ClientException {
		return linkFromResourceBlock(REGISTRY_TAG);
	}

	/**
	 * Gets the components link.
	 * 
	 * @return the components link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getComponentsLink() throws ClientException {
		return linkFromResourceBlock(COMPONENTS_TAG);
	}
}
