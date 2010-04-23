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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;

/**
 * The Class Event. An Event is any action performed on a registry. It is
 * exclusively used by Log.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Event extends Representation {
	private static final String ERROR_CANNOT_CONVERT_LOGGED_AT_TO_DATE = "Cannot convert logged_at to a date. Value: ";

	private static final String LOGGED_AT_TAG = "logged_at";
	private static final String RESOURCE_TAG = "resource";

	private String[] NEW_RESOURCES = { RESOURCE_TAG, LOGGED_AT_TAG };

	/**
	 * Instantiates a new event.
	 * 
	 * @param jo
	 *            the jo
	 */
	public Event(JSONObject jo, UsernamePasswordCredentials cred) {
		restContent = jo;
		credentials = cred;
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the resource.
	 * 
	 * @return the resource
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getResource() throws ClientException {
		return linkFromResourceBlock(RESOURCE_TAG);
	}

	/**
	 * Gets the logged date and time.
	 * 
	 * @return the Java Date representing the logged date and time
	 */
	public Date getLoggedAt() {
		SimpleDateFormat df = new SimpleDateFormat(CHEMCASTER_DATE_FORMAT);
		String createdString = getResource(LOGGED_AT_TAG);
		try {
			return df.parse(createdString);
		} catch (ParseException pe) {
			throw new ClientRuntimeException(
					ERROR_CANNOT_CONVERT_LOGGED_AT_TO_DATE + createdString);
		}
	}

}
