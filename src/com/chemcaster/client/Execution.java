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
 * The Class Execution. An Execution represents the execution of an executable
 * resource such as a Query. After creation, no attempt is made to store an
 * Execution; clients must any representation immediately. The media type is
 * [application/vnd.com.chemcaster.Execution+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Execution extends Item {
	
	public static final String EXECUTION_MEDIA_TYPE = "application/vnd.com.chemcaster.Execution+json";
	
	private static final String CURSOR_TAG = "cursor";
	private static final String EXECUTABLE_TAG = "executable";
	private static final String MAXIMUM_RESULTS_TAG = "maximum_results";
	private static final String NAME_TAG = "name";
	private static final String NEXT_CURSOR_TAG = "next_cursor";
	private static final String PREVIOUS_CURSOR_TAG = "previous_cursor";
	private static final String REVERSE_TAG = "reverse";
	private static final String SUBSTANCES_TAG = "substances";
	private static final String URI_TAG = "uri";
	private String[] NEW_ATTRIBUTES = { CURSOR_TAG, REVERSE_TAG,
			NEXT_CURSOR_TAG, PREVIOUS_CURSOR_TAG, MAXIMUM_RESULTS_TAG };
	private String[] NEW_RESOURCES = { EXECUTABLE_TAG, SUBSTANCES_TAG };

	/**
	 * Instantiates a new execution.
	 */
	public Execution() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}
	
	/**
	 * Gets the cursor.
	 * 
	 * @return the cursor
	 */
	public String getCursor() {
		return getAttribute(CURSOR_TAG);
	}

	/**
	 * Gets the maximum results.
	 * 
	 * @return the maximum results as a String
	 */
	public Number getMaximumResults() {
		return getAttribute(MAXIMUM_RESULTS_TAG);
	}

	/**
	 * Checks if the results will come back in reverse order.
	 * 
	 * @return the Boolean
	 */
	public Boolean isReverse() {
		return getAttribute(REVERSE_TAG);
	}

	/**
	 * Gets the next cursor.
	 * 
	 * @return the next cursor as a String
	 */
	public String getNextCursor() {
		return getAttribute(NEXT_CURSOR_TAG);
	}

	/**
	 * Gets the previous cursor.
	 * 
	 * @return the previous cursor as a String
	 */
	public String getPreviousCursor() {
		return getAttribute(PREVIOUS_CURSOR_TAG);
	}

	/**
	 * Gets the executable link.
	 * 
	 * @return the executable link
	 * 
	 * @throws ClientException the client exception
	 */
	public Link getExecutableLink() throws ClientException {
		return linkFromResourceBlock(EXECUTABLE_TAG);
	}

	/**
	 * Gets the substances as an ArrayList of ArrayList of Links. Each inner
	 * array item is related to its peers through a Structure. If a Registry
	 * contains only single-structure substances, each inner array will contain
	 * one matching Substance.
	 * 
	 * @return the substances as a ArrayList<ArrayList<Link>>
	 * 
	 * @throws ClientException the client exception in case of Link creation failure
	 */
	public ArrayList<ArrayList<Link>> getSubstancesLinks() throws ClientException {

		JSONArray substanceArray = getResource(SUBSTANCES_TAG);
		ArrayList<ArrayList<Link>> substanceLinks = new ArrayList<ArrayList<Link>>(
				substanceArray.length());

		try {
			for (int i = 0; i < substanceArray.length(); i++) {
				JSONArray substanceGroup = substanceArray.getJSONArray(i);
				ArrayList<Link> groupLinks = new ArrayList<Link>(substanceGroup
						.length());

				for (int j = 0; j < substanceGroup.length(); j++) {
					groupLinks.add(Link.create(substanceGroup
							.getJSONObject(j), credentials));
				}

				substanceLinks.add(groupLinks);
			}
		} catch (JSONException e) {
		}

		return substanceLinks;
	}

	/**
	 * Gets the NameURI list for this instance of Substances
	 * 
	 * @return the NameURI
	 * 
	 * @throws ClientException the client exception
	 */
	public NameURI getSubstances() throws ClientException {

		JSONArray substanceArray = getResource(SUBSTANCES_TAG);
		NameURI substances = new NameURI();

		try {
			for (int i = 0; i < substanceArray.length(); i++) {
				JSONArray substanceGroup = substanceArray.getJSONArray(i);
				JSONObject substance = substanceGroup.getJSONObject(0);

				substances.add(substance.getString(NAME_TAG), substance.getString(URI_TAG));
			}
		} catch (JSONException e) {
		}

		return substances;
	}
}
