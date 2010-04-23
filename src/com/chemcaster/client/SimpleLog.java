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
import java.util.Date;

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * The Class SimpleLog.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleLog {

	Log log;

	/**
	 * Instantiates a new simple log.
	 * 
	 * @param newLog
	 *            the new log
	 */
	public SimpleLog(Log newLog) {
		log = newLog;
	}

	/**
	 * Instantiates a new simple log.
	 * 
	 * @param logURI
	 *            the log URI
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleLog(String logURI, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link logLink = Link.create(Log.LOG_MEDIA_TYPE, logURI, cred);
		log = ClientHttp.get(logLink);
	}

	/**
	 * Gets the creation time/date for this log.
	 * 
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return log.getCreatedAt();
	}

	/**
	 * Gets the interval (number of seconds) for this log.
	 * 
	 * @return the interval
	 */
	public Number getInterval() {
		return log.getInterval();
	}
	
	/**
	 * Gets the underlying Log instance
	 * 
	 * @return the log instance
	 */
	public Log getLog() {
		return log;
	}
	
	/**
	 * Gets the SimpleRegistry parent for this log
	 * 
	 * @return the SimpleRegistry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistry() throws ClientException {
		Registry r = ClientHttp.get(log.getRegistryLink());
		return new SimpleRegistry(r);
	}

	/**
	 * Gets all the sibling logs
	 * 
	 * @return a list of log identifiers
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getLogs() throws ClientException {
		Index logsIndex = ClientHttp.get(log.getLogsLink());
		return logsIndex.getItemNameURI();
	}

	/**
	 * Gets the sibling SimpleLog with the uri logURI.
	 * 
	 * @param logURI
	 *            the log URI
	 * 
	 * @return the SimpleLog with URI
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleLog getSimpleLogWithURI(String logURI) throws ClientException {
		Link logLink = Link.create(Log.LOG_MEDIA_TYPE, logURI, log
				.getLinkToSelf().getAuthentication());
		Log log = ClientHttp.get(logLink);
		return new SimpleLog(log);
	}

	/**
	 * Gets the list of Registry Events in the current log
	 * 
	 * @return the ArrayList of Event objects.
	 * 
	 */
	public ArrayList<Event> getRegistryEvents() {
		return getEventsOfType(Registry.REGISTRY_MEDIA_TYPE);
	}

	/**
	 * Return the Registry object from the Registry event
	 * 
	 * @param event
	 *            the Event representing a registry action
	 * 
	 * @return a Registry object
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Registry getRegistry(Event event) throws ClientException {
		return (Registry) getRepresentation(event, Registry.REGISTRY_MEDIA_TYPE);
	}

	/**
	 * Gets the list of Registry Events in this log
	 * 
	 * @return the ArrayList of Event objects.
	 * 
	 */
	public ArrayList<Event> getRegistrationEvents() {
		return getEventsOfType(Registration.REGISTRATION_MEDIA_TYPE);
	}

	/**
	 * Return the Registration object from the Registration event
	 * 
	 * @param event
	 *            the Event representing a registration action
	 * 
	 * @return a Registration object
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Registration getRegistration(Event event) throws ClientException {
		return (Registration) getRepresentation(event,
				Registration.REGISTRATION_MEDIA_TYPE);
	}

	/**
	 * Gets the list of Query Events in this log
	 * 
	 * @return the ArrayList of Event objects.
	 * 
	 */
	public ArrayList<Event> getQueryEvents() {
		return getEventsOfType(Query.QUERY_MEDIA_TYPE);
	}

	/**
	 * Return the Query object from the Query event
	 * 
	 * @param event
	 *            the Event representing a query action
	 * 
	 * @return a Query object
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Query getQuery(Event event) throws ClientException {
		return (Query) getRepresentation(event, Query.QUERY_MEDIA_TYPE);
	}

	/**
	 * Gets the list of Archive Events in this log
	 * 
	 * @return the ArrayList of Event objects.
	 * 
	 */
	public ArrayList<Event> getArchiveEvents() {
		return getEventsOfType(Archive.ARCHIVE_MEDIA_TYPE);
	}

	/**
	 * Return the Archive object from the Archive event
	 * 
	 * @param event
	 *            the Event representing a archive action
	 * 
	 * @return a Archive object
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Archive getArchive(Event event) throws ClientException {
		return ClientHttp.get(event.getResource());
	}

	private ArrayList<Event> getEventsOfType(String mediaType) {
		ArrayList<Event> allEvents = log.getEventsList();
		ArrayList<Event> specificEvents = new ArrayList<Event>();

		for (int i = 0; i < allEvents.size(); i++) {
			try {
				Link currentLink = allEvents.get(i).getResource();
				if (currentLink.mediaTypeString.equals(mediaType)) {
					specificEvents.add(allEvents.get(i));
				}
			} catch (ClientException e) {
				// if it can't be converted to a link, ignore it
			}
		}

		return specificEvents;
	}

	private Representation getRepresentation(Event event, String mediaTypeString)
			throws ClientException {
		Link aLink = event.getResource();
		if (aLink.getMediaTypeString().equals(mediaTypeString)) {
			return ClientHttp.get(event.getResource());
		} else {
			throw new ClientException("Event media type ("
					+ aLink.getMediaTypeString()
					+ ") does not match requested media type ("
					+ mediaTypeString + ").");
		}
	}

}
