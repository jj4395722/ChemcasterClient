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

import org.apache.http.auth.UsernamePasswordCredentials;

/**
 * The Class SimpleQuery is a simplified API on Query.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleQuery {

	Query query;

	/**
	 * Instantiates a new SimpleQuery based on an existing Query instance.
	 * 
	 * @param newQuery
	 *            the new query
	 */
	public SimpleQuery(Query newQuery) {
		query = newQuery;
	}

	/**
	 * Instantiates a new SimpleQuery of an existing query.
	 * 
	 * @param queryURL
	 *            the existing query's url
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleQuery(String queryURL, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link qLink = Link.create(Query.QUERY_MEDIA_TYPE, queryURL, cred);
		query = ClientHttp.get(qLink);
	}

	/**
	 * Create a new query and instantiates a new SimpleQuery for it.
	 * 
	 * @param mode
	 *            the mode (type) of the new query
	 * @param serialization
	 *            the serialized version the query, typically molfile format
	 * @param sr
	 *            the sr
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleQuery(SimpleRegistry sr, String mode, String serialization)
			throws ClientException {
		AttributeHash newQueryAttribs = new AttributeHash();
		newQueryAttribs.put("mode", mode);
		newQueryAttribs.put("serialization", serialization);

		Registry r = sr.getRegistry();
		Index qIndex = ClientHttp.get(r.getQueriesLink());
		query = ClientHttp.post(qIndex.create(), newQueryAttribs);
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * Gets the mode.
	 * 
	 * @return the String
	 */
	public String getMode() {
		return query.getMode();
	}

	/**
	 * Gets the serialization used for the query.
	 * 
	 * @return the serialization as a String
	 */
	public String getSerialization() {
		return query.getSerialization();
	}

	/**
	 * Gets the URI for this resource.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = query.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Delete the query represented by this instance.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */

	public void deleteQuery() throws ClientException {
		Query delReg = ClientHttp.delete(query.destroy());
		query = delReg;
	}

	/**
	 * Gets the SimpleRegistry that owns this query.
	 * 
	 * @return a SimpleRegistry instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleRegistry getSimpleRegistry() throws ClientException {
		Registry r = ClientHttp.get(query.getRegistryLink());
		return new SimpleRegistry(r);
	}

	/**
	 * Gets the names of the executions.
	 * 
	 * @return the ArrayList<String> of execution names
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getExecutions() throws ClientException {
		Index executionsIndex = ClientHttp.get(query.getExecutionsLink());
		return executionsIndex.getItemNameURI();
	}

	/**
	 * Gets the SimpleExecution named by executionURI.
	 * 
	 * @param executionURI
	 *            the execution URI
	 * 
	 * @return the SimpleExecution instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleExecution getSimpleExecutionWithURI(String executionURI)
			throws ClientException {
		Link eLink = Link.create(Execution.EXECUTION_MEDIA_TYPE, executionURI,
				query.getLinkToSelf().getAuthentication());
		Execution e = ClientHttp.get(eLink);
		return new SimpleExecution(e);
	}

	/**
	 * Gets the SimpleExecution of the query.
	 * 
	 * @param maximumResults
	 *            the maximum results
	 * 
	 * @return the SimpleExecution
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleExecution getSimpleExecution(Number maximumResults)
			throws ClientException {
		Index executionsIndex = ClientHttp.get(query.getExecutionsLink());
		AttributeHash executionAttribs = new AttributeHash();
		executionAttribs.put("maximum_results", maximumResults);
		Execution e = ClientHttp.post(executionsIndex.create(),
				executionAttribs);
		return new SimpleExecution(e);
	}

	/**
	 * Create a SimpleImage of the query structure defined by the query image
	 * index link.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param format
	 *            the format
	 * 
	 * @return the SimpleImage
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleImage getQuerySimpleImage(Integer width, Integer height,
			String format) throws ClientException {
		return new SimpleImage(query.getImagesLink(), width, height, format);
	}
	
	/**
	 * Gets the names and URIs of the queries.
	 * 
	 * @return the NameURI instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public NameURI getQueries() throws ClientException {
		Index queriesIndex = ClientHttp.get(query.getIndexLink());
		return queriesIndex.getItemNameURI();
	}

	/**
	 * Gets the query identified by queryURI.
	 * 
	 * @param queryURI
	 *            the query URI
	 * 
	 * @return the Query instance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleQuery getSimpleQueryWithURI(String queryURI)
			throws ClientException {
		Link qLink = Link.create(Query.QUERY_MEDIA_TYPE, queryURI, query
				.getLinkToSelf().getAuthentication());
		Query q = ClientHttp.get(qLink);
		return new SimpleQuery(q);
	}

}
