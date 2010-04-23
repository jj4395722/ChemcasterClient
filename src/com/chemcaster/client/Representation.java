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

import java.io.PrintStream;
import java.util.HashSet;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The abstract class Representation. A representation is usually a set of
 * Chemcaster resource data and links. Not all representations are resources,
 * due to extraction of common behaviors.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public abstract class Representation {
	private static final int JSON_OUTPUT_INDENTATION = 3;

	private static final String MEDIA_TYPE_PREFIX = "application/vnd.com.chemcaster.";
	private static final String MEDIA_TYPE_SUFFIX = "+json";

	protected static final String CHEMCASTER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";
	protected static final String INDEX_TAG = "index";
	protected static final String MEDIA_TYPE_TAG = "media_type";
	protected static final String URI_TAG = "uri";

	Link selfLink;
	UsernamePasswordCredentials credentials;
	JSONObject restContent;
	String myLowerCaseName;
	HashSet<String> attributeSet = new HashSet<String>();
	HashSet<String> resourceSet = new HashSet<String>();

	/**
	 * Instantiates a new representation.
	 */
	public Representation() {
		myLowerCaseName = simpleClassName().toLowerCase();
	}

	/**
	 * Populate.
	 * 
	 * @param newLink
	 *            a Link
	 * @param newRestContent
	 *            new rest content as a JSONObject
	 */
	public void populate(Link newLink, JSONObject newRestContent) {
		selfLink = newLink;
		credentials = newLink.getAuthentication();
		restContent = newRestContent;
	}

	/**
	 * Gets this representation's original link.
	 * 
	 * @return the original representation link
	 */
	public Link getLinkToSelf() {
		return selfLink;
	}
	
	/**
	 * Gets the index link.
	 * 
	 * @return the index link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getIndexLink() throws ClientException {
		return linkFromResourceBlock(INDEX_TAG);
	}

	/**
	 * Dump rest content.
	 * 
	 * @param printToHere
	 *            the PrintStream used for printing
	 */
	public void dumpRestContent(PrintStream printToHere) {
		try {
			printToHere.println(restContent.toString(JSON_OUTPUT_INDENTATION));
		} catch (Exception e) {
		}
	}

	/**
	 * Adds the new attribute list.
	 * 
	 * @param keys
	 *            the array of attribute keys
	 */
	protected void addNewAttributeList(String[] keys) {
		for (String key : keys) {
			attributeSet.add(key);
		}
	}

	/**
	 * Adds the new valid attribute.
	 * 
	 * @param key
	 *            the attribute key
	 */
	protected void addNewAttribute(String key) {
		attributeSet.add(key);
	}

	/**
	 * Adds the new resource list.
	 * 
	 * @param keys
	 *            the array of resource keys
	 */
	protected void addNewResourceList(String[] keys) {
		for (String key : keys) {
			resourceSet.add(key);
		}
	}

	/**
	 * Adds a new valid resource.
	 * 
	 * @param key
	 *            the resource key
	 */
	protected void addNewResource(String key) {
		resourceSet.add(key);
	}

	/**
	 * Link from resource block.
	 * 
	 * @param key
	 *            the resource key
	 * 
	 * @return the link, or null if the resource isn't found.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	protected Link linkFromResourceBlock(String key) throws ClientException {
		JSONObject linkAttribs = getResource(key);
		return Link.create(linkAttribs, credentials);
	}

	/**
	 * Gets an attribute. Attributes are only found "underneath" the resource
	 * key of the class name.
	 * 
	 * @param key
	 *            the attribute key
	 * 
	 * @return the attribute, or null if unsuccessful or key is not found
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getAttribute(String key) {
		JSONObject attributes = (JSONObject) uncheckedGetObjectFromJSON(
				myLowerCaseName, restContent);
		return (T) getObjectFromJSON(key, attributes, attributeSet);
	}

	/**
	 * Gets a resource.
	 * 
	 * @param key
	 *            the resource key
	 * 
	 * @return the resource, or null if unsuccessful or key is not found
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getResource(String key) {
		return (T) getObjectFromJSON(key, restContent, resourceSet);
	}

	/**
	 * Gets the object from json.
	 * 
	 * @param key
	 *            the attribute key
	 * @param jObject
	 *            the JSON object
	 * @param validKeys
	 *            the valid keys in the JSON object, set up at the resource
	 *            creation time
	 * 
	 * @return the object from JSON, or null if unsuccessful or key is not found
	 */
	protected Object getObjectFromJSON(String key, JSONObject jObject,
			HashSet<String> validKeys) {
		if (validKeys.contains(key)) {
			return uncheckedGetObjectFromJSON(key, jObject);
		} else {
			return null;
		}
	}

	/**
	 * Unchecked get object from json.
	 * 
	 * @param key
	 *            attribute key
	 * @param jObject
	 *            the attribute JSON object
	 * 
	 * @return the attribute JSON object, or null if unsuccessful or key is not
	 *         found
	 */
	protected Object uncheckedGetObjectFromJSON(String key, JSONObject jObject) {
		try {
			if (jObject != null && jObject.has(key)) {
				Object result = jObject.get(key);
				if (result == JSONObject.NULL) {
					result = null;
				}
				return result;
			} else {
				return null;
			}
		} catch (JSONException je) {
			return null;
		}
	}

	/**
	 * Simple class name.
	 * 
	 * @return the simple class name without encoded path info
	 */
	protected String simpleClassName() {
		String qualifiedName = qualifiedClassName();
		return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
	}

	/**
	 * Qualified class name.
	 * 
	 * @return the fully qualified class string, with path
	 */
	protected String qualifiedClassName() {
		return this.getClass().getName();
	}

	/**
	 * Resource string.
	 * 
	 * @param resourceName
	 *            the resource name
	 * 
	 * @return the full resource string.
	 */
	protected String resourceString(String resourceName) {
		return MEDIA_TYPE_PREFIX + resourceName + MEDIA_TYPE_SUFFIX;
	}
}
