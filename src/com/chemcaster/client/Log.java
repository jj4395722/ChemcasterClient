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
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * The Class Log. A Log represents the set of changes made to a Registry, and
 * all of its Registrations, Queries, and Archives within a given time interval.
 * A Log is typically used when synchronizing multiple applications using the
 * same Registry. The media type is [application/vnd.com.chemcaster.Log+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Log extends Representation {
	public static final String LOG_MEDIA_TYPE = "application/vnd.com.chemcaster.Log+json";

	private static final String ERROR_EVENTS_ITEM_ACCESS = "Unable to access events item: ";
	private static final String ERROR_CREATED_AT_TO_DATE_CONVERSION = "Cannot convert created_at to a date. Value: ";

	private static final String CHEMCASTER_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";

	private static final String CREATED_AT_TAG = "created_at";
	private static final String EVENTS_TAG = "events";
	private static final String INTERVAL_TAG = "interval";
	private static final String LOGS_TAG = "logs";
	private static final String REGISTRY_TAG = "registry";

	private String[] NEW_ATTRIBUTES = { CREATED_AT_TAG, INTERVAL_TAG };
	private String[] NEW_RESOURCES = { EVENTS_TAG, INDEX_TAG, REGISTRY_TAG };

	/**
	 * Instantiates a new log.
	 */
	public Log() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the log creation date. Sample internal format: <b> 2009-10-19
	 * 22:40:00 UTC </b> Note that if nothing special except .toString() is done
	 * to this object, it will convert to the system time zone.
	 * 
	 * @return a java Date of log creation
	 */
	public Date getCreatedAt() {
		SimpleDateFormat df = new SimpleDateFormat(CHEMCASTER_LOG_DATE_FORMAT);
		String createdString = getAttribute(CREATED_AT_TAG);
		try {
			return df.parse(createdString);
		} catch (ParseException pe) {
			throw new ClientRuntimeException(
					ERROR_CREATED_AT_TO_DATE_CONVERSION + createdString);
		}
	}

	/**
	 * Gets the interval.
	 * 
	 * @return the interval
	 */
	public Number getInterval() {
		return getAttribute(INTERVAL_TAG);
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
	 * Fill events list.
	 */
	public ArrayList<Event> getEventsList() {
		ArrayList<Event> eventsList = new ArrayList<Event>();

		JSONArray events = getResource(EVENTS_TAG);

		for (int currentLink = 0; currentLink < events.length(); currentLink++) {
			try {
				eventsList.add(new Event(events.getJSONObject(currentLink), selfLink.getAuthentication()));
			} catch (JSONException je) {
				throw new ClientRuntimeException(ERROR_EVENTS_ITEM_ACCESS
						+ je.getMessage());
			}
		}
		return eventsList;
	}
	
	/**
	 * Gets the logs link.
	 * 
	 * @return the logs index link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */	
	public Link getLogsLink() throws ClientException {
		return linkFromResourceBlock(LOGS_TAG);
	}
}
