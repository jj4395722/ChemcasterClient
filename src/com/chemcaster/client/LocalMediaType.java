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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class LocalMediaType. LocalMediaType is an abstract factory (GOF) for
 * Representations, keyed off of the media_type_string. Decode the media_type
 * string of a response into a Representation class, fill with response data.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class LocalMediaType {

	private static final String ERROR_CANT_CREATE_INSTANCE = "Unable to create a class instance from media type: ";
	private static final String ERROR_MEDIA_TYPE_FORMAT = "Bad format for media type: ";
	private static final String ERROR_NO_EXISTING_MEDIA_TYPE = "No such media type exists ";

	private static final String APPLICATION_ZIP = "application/zip";
	private static final String MEDIA_CLASS_PREFIX = "com.chemcaster.client.";
	private static final String URI_REGEXP = "application/vnd\\.com\\.chemcaster\\.(.*)\\+json";

	private static final String ZIP_CLASS_NAME = "Zip";

	/**
	 * Creates the representation instance based on the media_type string.
	 * 
	 * @param mediaClassString
	 *            the media class string
	 * 
	 * @return the representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public static Representation createRepresentationInstance(
			String mediaClassString) throws ClientException {
		String mediaClassName = qualifiedMediaClassName(mediaClassString);

		try {
			// implementation from http://kickjava.com/1139.htm
			return (Representation) Class.forName(mediaClassName, true,
					ClassLoader.getSystemClassLoader()).newInstance();
		} catch (ClassNotFoundException notFoundException) {
			throw new ClientException(ERROR_NO_EXISTING_MEDIA_TYPE
					+ mediaClassString);
		} catch (Exception e) {
			throw new ClientException(ERROR_CANT_CREATE_INSTANCE
					+ mediaClassString);
		}
	}

	/**
	 * Generate hash key based on the mime type. Validate the mime type by
	 * trying to create a Representation instance.
	 * 
	 * @param fullMimeTypeName
	 *            the full mime type name
	 * 
	 * @return the hash key or null if the mime type cannot be decoded.
	 */
	public static String generateHashKey(String fullMimeTypeName) {
		try {
			createRepresentationInstance(fullMimeTypeName);
			return simpleMediaClassName(fullMimeTypeName).toLowerCase();
		} catch (ClientException ce) {
			return null;
		}
	}

	/**
	 * Generate a qualified media class name, with full path, based on a
	 * media_type string.
	 * 
	 * @param fullMediaType
	 *            the full media type
	 * 
	 * @return the string
	 */
	protected static String qualifiedMediaClassName(String fullMediaType) {
		return MEDIA_CLASS_PREFIX + simpleMediaClassName(fullMediaType);
	}

	/**
	 * Generate the class name (just the name, not a fully qualified one), based
	 * on a media_type string.
	 * 
	 * @param fullMediaType
	 *            the full media type
	 * 
	 * @return the string
	 */
	protected static String simpleMediaClassName(String fullMediaType) {
		String derivedClassName = null;

		if (fullMediaType.equals(APPLICATION_ZIP)) {
			derivedClassName = ZIP_CLASS_NAME;
		} else {
			final Matcher uriMatch = Pattern.compile(URI_REGEXP).matcher(
					fullMediaType);

			if (uriMatch.find()) {
				derivedClassName = uriMatch.group(1);
			} else {
				throw new ClientRuntimeException(ERROR_MEDIA_TYPE_FORMAT
						+ fullMediaType);
			}
		}

		return derivedClassName;
	}

}
