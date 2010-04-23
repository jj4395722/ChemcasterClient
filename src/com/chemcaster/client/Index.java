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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class Index. A list of all available specific instances of a given
 * Representation. Typically, a GET call to a Representation Link will return an
 * index of specific instances of that Representation. The media type is
 * [application/vnd.com.chemcaster.Index+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Index extends Representation {
	
	private static final String UNABLE_TO_ACCESS_INDEX_ITEM = "Unable to access index item: ";
	private static final String JSON_ITEM_NAME = "name";
	private static final String JSON_ITEM_URI = "uri";
	protected static final String CREATE_TAG = "create";
	protected static final String ITEMS_TAG = "items";
	protected static final String PARENT_TAG = "parent";
	protected static final String NEXTPAGE_TAG = "next_page";
	protected String[] NEW_RESOURCES = { ITEMS_TAG, CREATE_TAG, PARENT_TAG,
			NEXTPAGE_TAG };

	/**
	 * Instantiates a new index.
	 */
	public Index() {
		super();
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Get the item links list.
	 * 
	 * @return the ArrayList<Link>
	 * 
	 * @throws ClientException the client exception
	 */

	public ArrayList<Link> getItemLinksList() throws ClientException {
		JSONArray links = getResource(ITEMS_TAG);
		ArrayList<Link> itemLinks = new ArrayList<Link>(links.length());

		for (int i = 0; i < links.length(); i++) {
			itemLinks.add(getLink(links, i));
		}
		return itemLinks;
	}

	/**
	 * Gets the item names and URIs as a NameURI instance
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException the client exception
	 */
	public NameURI getItemNameURI() throws ClientException {
		JSONArray links = getResource(ITEMS_TAG);
		NameURI items = new NameURI();

		for (int i = 0; i < links.length(); i++) {
			items.add(getLinkName(links, i), getLinkURI(links,i));
		}

		return items;
	}

	/**
	 * Gets the link of the item whose name is in targetName.
	 * 
	 * @param targetName the name of the item 
	 * 
	 * @return the Link instance of the item
	 * 
	 * @throws ClientException the client exception
	 */
	public Link getItemNamed(String targetName) throws ClientException {
		JSONArray links = getResource(ITEMS_TAG);
		for (int i = 0; i < links.length(); i++) {
			if (targetName.equals(getLinkName(links, i))) {
				return getLink(links, i);
			}
		}
		return null;
	}
	
	/**
	 * Gets the link name for a given index.
	 * 
	 * @param jArray the JSON array
	 * @param index the index
	 * 
	 * @return the link name
	 */
	private String getLinkName(JSONArray jArray, int index) {
		try {
			return jArray.getJSONObject(index).getString(JSON_ITEM_NAME);
		} catch (JSONException je) {
			throw new ClientRuntimeException(UNABLE_TO_ACCESS_INDEX_ITEM
					+ je.getMessage());
		}
	}
	
	/**
	 * Gets the link name for a given index.
	 * 
	 * @param jArray the JSON array
	 * @param index the index
	 * 
	 * @return the link name
	 */
	private String getLinkURI(JSONArray jArray, int index) {
		try {
			return jArray.getJSONObject(index).getString(JSON_ITEM_URI);
		} catch (JSONException je) {
			throw new ClientRuntimeException(UNABLE_TO_ACCESS_INDEX_ITEM
					+ je.getMessage());
		}
	}
	
	/**
	 * Gets the Link instance for a given index.
	 * 
	 * @param jArray the JSON array
	 * @param index the index
	 * 
	 * @return the link
	 */
	private Link getLink(JSONArray jArray, int index) {
		try {
			return Link.create(jArray.getJSONObject(index), credentials);
		} catch (JSONException je) {
			throw new ClientRuntimeException(UNABLE_TO_ACCESS_INDEX_ITEM
					+ je.getMessage());
		}
	}

	/**
	 * Gets the creation Link.
	 * 
	 * @return the Link instance for creation of this Representation
	 * 
	 * @throws ClientException the client exception
	 */
	public Link create() throws ClientException {
		JSONObject linkAttribs = getResource(CREATE_TAG);
		return Link.create(linkAttribs, credentials);
	}

	/**
	 * Gets the parent Link.
	 * 
	 * @return the parent
	 * 
	 * @throws ClientException the client exception
	 */
	public Link getParent() throws ClientException {
		return linkFromResourceBlock(PARENT_TAG);
	}

	/**
	 * Gets the next page Link.
	 * 
	 * @return the next page
	 * 
	 * @throws ClientException the client exception
	 */
	public Link getNextPage() throws ClientException {
		return linkFromResourceBlock(NEXTPAGE_TAG);
	}
}
