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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class Link. This is a simple class with getters, and a validation
 * routine. Note: The original HTTP stuff (as per the Ruby implementation) was
 * refactored into ClientHttp.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Link {

	private static final String ERROR_NO_IDENTITY = "Initial UsernamePasswordCredentials object is empty.";
	private static final String ERROR_NO_MEDIA_TYPE = "Initial media_type string is null or empty.";
	private static final String ERROR_NO_URI = "Initial uri string is null or empty.";
	private static final String MEDIA_TYPE_TAG = "media_type";
	private static final String NAME_TAG = "name";
	private static final String URI_TAG = "uri";

	String uri;
	String mediaTypeString;
	String name;
	UsernamePasswordCredentials localCredentials;

	/**
	 * Creates a Link from a media_type, uri, and an UsernamePasswordCredentials
	 * object. Links must be formed using one of the two creates, as there is no
	 * "new" method for this class,
	 * 
	 * @param userMediaTypeString
	 *            a media type
	 * @param userURI
	 *            the uri
	 * @param userId
	 *            the UsernamePasswordCredentials object
	 * 
	 * @return a Link
	 */
	public static Link create(String userMediaTypeString, String userURI,
			UsernamePasswordCredentials userId) {
		return new Link(userMediaTypeString, userURI, userId);
	}

	/**
	 * Creates a Link from a JSONObject and an UsernamePasswordCredentials
	 * object. Links must be formed using one of the two creates, as there is no
	 * "new" method for this class,
	 * 
	 * @param someJson
	 *            the JSONObject containing information for creating the link.
	 * @param userId
	 *            the UsernamePasswordCredentials object
	 * 
	 * @return a Link, or null, if the JSONObject is null
	 */
	public static Link create(JSONObject someJson,
			UsernamePasswordCredentials userId) {
		if (someJson == null) {
			return null;
		} else {
			return new Link(someJson, userId);
		}
	}

	/**
	 * Privately instantiates a new link from a media_type, uri, and an
	 * UsernamePasswordCredentials object..
	 * 
	 * @param userMediaTypeString
	 *            a media type
	 * @param userURI
	 *            the uri
	 * @param userId
	 *            the UsernamePasswordCredentials object
	 */
	private Link(String userMediaTypeString, String userURI,
			UsernamePasswordCredentials userId) {
		clearInstanceVars();

		mediaTypeString = userMediaTypeString;
		uri = userURI;
		localCredentials = userId;
	}

	/**
	 * Privately instantiates a new link from a JSONObject and an
	 * UsernamePasswordCredentials object..
	 * 
	 * @param someJson
	 *            the JSONObject containing information for creating the link.
	 * @param userId
	 *            the UsernamePasswordCredentials object
	 */
	private Link(JSONObject someJson, UsernamePasswordCredentials userId) {
		clearInstanceVars();

		try {
			mediaTypeString = someJson.getString(MEDIA_TYPE_TAG);
			uri = someJson.getString(URI_TAG);
			name = someJson.getString(NAME_TAG);
		} catch (JSONException je) {
		}

		localCredentials = userId;
	}

	/**
	 * Gets the media type string.
	 * 
	 * @return the media type string
	 */
	public String getMediaTypeString() {
		return mediaTypeString;
	}

	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * Gets the name. This parameter is only set when the link is created
	 * through a JSON object.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the uRI host.
	 * 
	 * @return the uRI host
	 */
	public String getURIHost() {
		try {
			URL u = new URL(uri);
			return u.getHost();
		} catch (MalformedURLException e) {
			throw new ClientRuntimeException(e.getMessage());
		}
	}

	/**
	 * Gets the authentication based on the UsernamePasswordCredentials object.
	 * 
	 * @return the authentication
	 */
	public UsernamePasswordCredentials getAuthentication() {
		return localCredentials;
	}

	/**
	 * Clear instance vars.
	 */
	private void clearInstanceVars() {
		mediaTypeString = null;
		uri = null;
		localCredentials = null;
	}

	/**
	 * Validate instance variables before Link execution.
	 * 
	 * @throws ClientException
	 *             if it can't find any of the necessary parameters
	 */
	public void validateInstanceVars() throws ClientException {
		if (mediaTypeString == null || mediaTypeString.isEmpty()) {
			throw new ClientException(ERROR_NO_MEDIA_TYPE);
		}
		if (uri == null || uri.isEmpty()) {
			throw new ClientException(ERROR_NO_URI);
		}
		if (localCredentials == null) {
			throw new ClientException(ERROR_NO_IDENTITY);
		}
	}

}
