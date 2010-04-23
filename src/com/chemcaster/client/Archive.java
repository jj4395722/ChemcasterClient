/*
* ChemcasterClient - a Java interface to the REST services of 
* http://www.chemcaster.com. Based on chemcaster-ruby
* (Copyright (c) 2009 Metamolecular LLC - http://www.metamolecular.com).
*
* Copyright (c) 2009 John Jaeger <jj4395722_at_yahoo_dot_com>
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

/**
 * The Class Archive. An Archive represents a snapshot of the current state of a
 * Registry and its subordinate resources. Archives exist until deleted, either
 * manually though the administrative interface or an API call, or through an
 * automatic sweeper process. The maximum expected time of residency for an
 * archive on the service is 24 hours.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Archive extends Representation {
	public static final String ARCHIVE_MEDIA_TYPE = "application/vnd.com.chemcaster.Archive+json";	

	private static final String NO_DATE_CONVERSION = "Cannot convert value to a date. Value: ";

	protected static final String CHEMCASTER_ARCHIVE_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";

	private static final String CREATED_AT_TAG = "created_at";
	private static final String DESTROY_TAG = "destroy";
	private static final String DONE_TAG = "done";
	private static final String REGISTRY_TAG = "registry";
	private static final String ZIPFILE_TAG = "zipfile";

	private String[] NEW_ATTRIBUTES = { CREATED_AT_TAG, DONE_TAG };
	private String[] NEW_RESOURCES = { ZIPFILE_TAG, REGISTRY_TAG, DESTROY_TAG };

	/**
	 * Instantiates a new archive.
	 */
	public Archive() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the created date/time.
	 * 
	 * @return the Java Date representing the created date and time
	 */
	public Date getCreatedAt() {
		SimpleDateFormat df = new SimpleDateFormat(
				CHEMCASTER_ARCHIVE_DATE_FORMAT);
		String createdString = getAttribute(CREATED_AT_TAG);
		try {
			return df.parse(createdString);
		} catch (ParseException pe) {
			throw new ClientRuntimeException(NO_DATE_CONVERSION + createdString);
		}
	}

	/**
	 * Checks if archive creation is done.
	 * 
	 * @return the boolean
	 */
	public Boolean isDone() {
		return getAttribute(DONE_TAG);
	}

	/**
	 * Destroy.
	 * 
	 * @return the link instance for a DELETE call
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getDestroyLink() throws ClientException {
		return linkFromResourceBlock(DESTROY_TAG);
	}

	/**
	 * Gets the zipfile link.
	 * 
	 * @return the zipfile link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getZipfileLink() throws ClientException {
		return linkFromResourceBlock(ZIPFILE_TAG);
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
}
