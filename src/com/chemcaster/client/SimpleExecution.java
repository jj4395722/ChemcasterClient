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

/**
 * The Class SimpleExecution.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleExecution {

	/** The execution. */
	Execution execution;

	/**
	 * Instantiates a new SimpleExecution.
	 * 
	 * @param newExecution the new execution
	 */
	public SimpleExecution(Execution newExecution) {
		execution = newExecution;
	}

	// this 406s, so isn't valid
	// public SimpleExecution(String executionURI, String username, String
	// password)

	/**
	 * Gets the execution.
	 * 
	 * @return the execution
	 */
	public Execution getExecution() {
		return execution;
	}

	/**
	 * Gets the SimpleQuery.
	 * 
	 * @return the SimpleQuery
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleQuery getSimpleQuery() throws ClientException {
		Query q = ClientHttp.get(execution.getExecutableLink());
		return new SimpleQuery(q);
	}

	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = execution.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Gets the cursor.
	 * 
	 * @return the cursor
	 */
	public String getCursor() {
		return execution.getCursor();
	}

	/**
	 * Checks if is reverse.
	 * 
	 * @return the boolean
	 */
	public Boolean isReverse() {
		return execution.isReverse();
	}

	/**
	 * Gets the maximum results.
	 * 
	 * @return the maximum results
	 */
	public Number getMaximumResults() {
		return execution.getMaximumResults();
	}

	/**
	 * Gets the next cursor.
	 * 
	 * @return the next cursor
	 */
	public String getNextCursor() {
		return execution.getNextCursor();
	}

	/**
	 * Gets the previous cursor.
	 * 
	 * @return the previous cursor
	 */
	public String getPreviousCursor() {
		return execution.getPreviousCursor();
	}

	/**
	 * Gets the executable.
	 * 
	 * @return the executable
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleQuery getExecutable() throws ClientException {
		Query q = ClientHttp.get(execution.getExecutableLink());
		return new SimpleQuery(q);
	}

	/**
	 * Next results from the query execution.
	 * 
	 * @param cursor the cursor
	 * @param reverse the reverse
	 * @param maximumResults the maximum results
	 * 
	 * @return the simple execution
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleExecution nextResults(String cursor, Boolean reverse,
			Number maximumResults) throws ClientException {
		AttributeHash executionAttribs = new AttributeHash();
		executionAttribs.put("maximum_results", maximumResults);
		executionAttribs.put("cursor", cursor);
		executionAttribs.put("reverse", reverse);

		Index eIndex = ClientHttp.get(execution.getIndexLink());
		Execution e = ClientHttp.post(eIndex.create(), executionAttribs);
		return new SimpleExecution(e);
	}

	/**
	 * Gets the substances.
	 * 
	 * @return the substances as a NameURI 
	 * 
	 * @throws ClientException the client exception
	 */
	public NameURI getSubstances() throws ClientException {
		return execution.getSubstances();
	}

	/**
	 * Gets the SimpleSubstance from a URI.
	 * 
	 * @param substanceURI the substance URI
	 * 
	 * @return the simple substance with URI
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleSubstance getSimpleSubstanceWithURI(String substanceURI)
			throws ClientException {
		Link sLink = Link.create(Substance.SUBSTANCE_MEDIA_TYPE, substanceURI,
				execution.getLinkToSelf().getAuthentication());
		Substance s = ClientHttp.get(sLink);
		return new SimpleSubstance(s);
	}
}
