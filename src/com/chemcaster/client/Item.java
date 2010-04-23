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
 * The abstract class Item is a base for many of the other Representation
 * subclasses, and adds update() and destroy() methods to the basic
 * Representation.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public abstract class Item extends Representation {
	private static final String DESTROY_TAG = "destroy";
	private static final String UPDATE_TAG = "update";

	protected String[] NEW_RESOURCES = { UPDATE_TAG, DESTROY_TAG };

	/**
	 * Instantiates a new Item.
	 */
	public Item() {
		super();
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Update.
	 * 
	 * @return the Link instance to a PUT call
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link update() throws ClientException {
		return linkFromResourceBlock(UPDATE_TAG);
	}

	/**
	 * Destroy.
	 * 
	 * @return the Link instance for a DELETE call
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link destroy() throws ClientException {
		return linkFromResourceBlock(DESTROY_TAG);
	}
}
