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
 * The Class Registration. A Regstration represents the act of registering a
 * chemical substance. The media type is
 * [application/vnd.com.chemcaster.Registration+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Registration extends Item {
	public static final String REGISTRATION_MEDIA_TYPE = "application/vnd.com.chemcaster.Registration+json";

	private static final String REGISTRY_TAG = "registry";
	private static final String SUBSTANCE_TAG = "substance";
	private static final String TEMPLATES_TAG = "templates";

	private final String[] myAttributes = { TEMPLATES_TAG };
	private final String[] myResources = { INDEX_TAG, REGISTRY_TAG,
			SUBSTANCE_TAG };

	/**
	 * Instantiates a new registration.
	 */
	public Registration() {
		super();
		addNewAttributeList(myAttributes);
		addNewResourceList(myResources);
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
	 * Fill template.
	 * 
	 * @return the linked list< component template>
	 */
	public ArrayList<ComponentTemplate> getTemplatesList() {
		JSONArray anArray = getAttribute(TEMPLATES_TAG);
		ArrayList<ComponentTemplate> templates = new ArrayList<ComponentTemplate>(
				anArray.length());

		try {
			JSONObject aTemplate = anArray.getJSONObject(0);
			templates.add(new ComponentTemplate(aTemplate));
		} catch (JSONException je) {
		}

		return templates;
	}

}
