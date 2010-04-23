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

import java.io.InputStream;
import java.util.Date;

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * The Class SimpleArchive.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleArchive {

	Archive archive;

	/**
	 * Instantiates a new SimpleArchive from an Archive instance.
	 * 
	 * @param newArchive
	 *            the new archive
	 */
	public SimpleArchive(Archive newArchive) {
		archive = newArchive;
	}

	/**
	 * Instantiates a new SimpleArchive from a URI, username, and password.
	 * 
	 * @param archiveURI
	 *            the existing archive URI
	 * @param username
	 *            valid username
	 * @param password
	 *            valid password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleArchive(String archiveURI, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link arcLink = Link
				.create(Archive.ARCHIVE_MEDIA_TYPE, archiveURI, cred);
		archive = ClientHttp.get(arcLink);
	}

	/**
	 * Gets the archive.
	 * 
	 * @return the archive
	 */
	public Archive getArchive() {
		return archive;
	}

	/**
	 * Checks if archive creation is done.
	 * 
	 * @return the boolean
	 */
	public Boolean isDone() {
		return archive.isDone();
	}
	
	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = archive.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Gets the created date/time.
	 * 
	 * @return the Java Date representing the created date and time
	 */
	public Date getCreatedAt() {
		return archive.getCreatedAt();
	}

	/**
	 * Delete archive.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public void deleteArchive() throws ClientException {
		Archive delArch = ClientHttp.delete(archive.getDestroyLink());
		archive = delArch;
	}

	/**
	 * Gets the archive zipfile as an InputStream.
	 * 
	 * @return the archive zipfile as an InputStream
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public InputStream getZipfile() throws ClientException {
		Zip zip = ClientHttp.get(archive.getZipfileLink());
		return zip.getDataStream();
	}

	/**
	 * Gets the SimpleRegistry of this archive.
	 * 
	 * @return the SimpleRegistry
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistry() throws ClientException {
		Registry r = ClientHttp.get(archive.getRegistryLink());
		return new SimpleRegistry(r);
	}

}
