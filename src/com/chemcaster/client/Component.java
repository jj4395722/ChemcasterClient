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
 * The Class Component. A Component represents a component of a chemical
 * substance. A Component consists of one Structure, one Substance, and a
 * multiplier. All multipliers for a given Substance total 1.00. The media type
 * is [application/vnd.com.chemcaster.Component+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Component extends Representation {
	public static final String COMPONENT_MEDIA_TYPE = "application/vnd.com.chemcaster.Component+json";

	private static final String MULTIPLIER_TAG = "multiplier";
	private static final String STRUCTURE_TAG = "structure";
	private static final String SUBSTANCE_TAG = "substance";

	private String[] NEW_ATTRIBUTES = { MULTIPLIER_TAG };
	private String[] NEW_RESOURCES = { STRUCTURE_TAG, SUBSTANCE_TAG };

	/**
	 * Instantiates a new component.
	 */
	public Component() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the multiplier for this component.
	 * 
	 * @return the multiplier
	 */
	public Number getMultiplier() {
		return getAttribute(MULTIPLIER_TAG);
	}

	/**
	 * Gets the structure link.
	 * 
	 * @return the structure link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getStructureLink() throws ClientException {
		return linkFromResourceBlock(STRUCTURE_TAG);
	}

	/**
	 * Gets the substance link.
	 * 
	 * @return the substance link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getSubstanceLink() throws ClientException {
		return linkFromResourceBlock(SUBSTANCE_TAG);
	}
	
	/**
	 * Returns null, as there is no Index resource for Component
	 */
	public Link getIndexLink() {
		return null;
	}
}
