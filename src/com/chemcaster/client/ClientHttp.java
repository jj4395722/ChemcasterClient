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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ClientHttp is a utility class for http/https access.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public final class ClientHttp {

	private static final Boolean HTTP_REQUEST_DEBUG = false;

	private static final String ERROR_ACCESS_RESPONSE = "Unable to access response to request: ";
	private static final String ERROR_CANNOT_PROCESS = "The request was well-formed but was unable to be followed due to semantic errors. ";
	private static final String ERROR_INTERNAL_SERVER = "The server encountered an unexpected condition which prevented it from fulfilling the request. ";
	private static final String ERROR_NOT_ACCEPTABLE = "The resource identified by this request is not capable of generating a representation corresponding to one of the media types in the Accept header of the request. ";
	private static final String ERROR_NOT_ALLOWED = "The HTTP verb specified in the request (DELETE, GET, HEAD, POST, PUT) is not supported for this request URI. ";
	private static final String ERROR_NOT_FOUND = "The request specified a URI of a resource that does not exist. ";
	private static final String ERROR_NO_JSON_OBJECT_FROM_RESPONSE = "Unable to create JSONObject from response. ";
	private static final String ERROR_UNAUTHORIZED = "The authentication credentials included with this request are missing or invalid.";
	private static final String ERROR_UNEXPECTED = "Unexpected status: ";

	// HTTP codes 200,201,202
	private static final HashSet<Integer> GOOD_STATUS = new HashSet<Integer>() {
		private static final long serialVersionUID = 1L;
		{
			add(HttpStatus.SC_OK);
			add(HttpStatus.SC_CREATED);
			add(HttpStatus.SC_ACCEPTED);
		}
	};

	private static final String ACCEPT_HEADER = "Accept";
	private static final String CONTENT_TYPE = "Content-Type";

	private static final int SSH_PORT = 443;

	/**
	 * REST GET.
	 * 
	 * @param aLink
	 *            the link from a get
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T get(Link aLink)
			throws ClientException {
		HttpResponse response = createResponse(new HttpGet(aLink.getURI()),
				aLink);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * REST PUT, with no attributes.
	 * 
	 * @param aLink
	 *            the link for putting
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T put(Link aLink)
			throws ClientException {
		HttpResponse response = createResponse(new HttpPut(aLink.getURI()),
				aLink, null);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * REST PUT, with attributes.
	 * 
	 * @param attributes
	 *            the attributes of the put as an AttributeHash
	 * @param aLink
	 *            the link for putting
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T put(Link aLink,
			AttributeHash attributes) throws ClientException {
		HttpResponse response = createResponse(new HttpPut(aLink.getURI()),
				aLink, attributes);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * REST POST, with no attributes.
	 * 
	 * @param aLink
	 *            the link for posting
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T post(Link aLink)
			throws ClientException {
		HttpResponse response = createResponse(new HttpPost(aLink.getURI()),
				aLink, null);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * REST POST, with attributes.
	 * 
	 * @param attributes
	 *            the attributes of the post as an AttributeHash
	 * @param aLink
	 *            the link for posting
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T post(Link aLink,
			AttributeHash attributes) throws ClientException {
		HttpResponse response = createResponse(new HttpPost(aLink.getURI()),
				aLink, attributes);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * REST DELETE.
	 * 
	 * @param aLink
	 *            the link for resource deletion
	 * 
	 * @return the specific subclass of Representation
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Representation> T delete(Link aLink)
			throws ClientException {
		HttpResponse response = createResponse(new HttpDelete(aLink.getURI()),
				aLink);
		return (T) handleResponse(response, aLink);
	}

	/**
	 * Creates the response from GET and DELETE - with no additional passed
	 * params.
	 * 
	 * @param restAction
	 *            the rest action (GET, DELETE)
	 * @param aLink
	 *            the link for GET or DELETE action
	 * 
	 * @return the response from GET or DELETE
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	protected static HttpResponse createResponse(HttpRequestBase restAction,
			Link aLink) throws ClientException {

		aLink.validateInstanceVars();

		try {
			return clientResponse(restAction, aLink);
		} catch (ClientException ce) {
			throw ce;
		}
	}

	/**
	 * Creates the response from PUT and POST - with possible additional passed
	 * params in an AttributeHash
	 * 
	 * @param restAction
	 *            the rest action (PUT, POST)
	 * @param aLink
	 *            the link for PUT or POST action
	 * @param attributes
	 *            the attributes as an AttributeHash (or null)
	 * 
	 * @return the response from PUT or POST
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	protected static HttpResponse createResponse(
			HttpEntityEnclosingRequestBase restAction, Link aLink,
			AttributeHash attributes) throws ClientException {

		aLink.validateInstanceVars();

		if (attributes != null) {

			String requestName = LocalMediaType.generateHashKey(aLink
					.getMediaTypeString());
			AttributeHash finalHash = new AttributeHash();
			finalHash.put(requestName, attributes);
			JSONObject requestBlock = new JSONObject(finalHash);
			StringEntity stringEntity;

			try {
				stringEntity = new StringEntity(requestBlock.toString(),
						HTTP.UTF_8);
				stringEntity.setContentType(aLink.getMediaTypeString());
			} catch (UnsupportedEncodingException e) {
				throw new ClientRuntimeException(e.getMessage());
			}

			restAction.setEntity(stringEntity);
		}
		try {
			return clientResponse(restAction, aLink);
		} catch (ClientException ce) {
			throw ce;
		}
	}

	/**
	 * Handle response from the createResponse methods, returning a
	 * populatedRepresentation subclass.
	 * 
	 * @param response
	 *            a previously generated HttpResponse
	 * @param aLink
	 *            the link where the action occurred
	 * 
	 * @return the Representation (actually a subclass, but returned as the
	 *         generic parent)
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	protected static Representation handleResponse(HttpResponse response,
			Link aLink) throws ClientException {
		try {
			StatusLine responseStatus = response.getStatusLine();

			if (GOOD_STATUS.contains(responseStatus.getStatusCode())) {
				Representation decodedResponse = decodeResponse(response, aLink);
				response = null;
				return decodedResponse;
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				// 401
				throw new ClientException(ERROR_UNAUTHORIZED + responseStatus);
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				// 404
				throw new ClientException(ERROR_NOT_FOUND + responseStatus);
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_METHOD_NOT_ALLOWED) {
				// 405
				throw new ClientException(ERROR_NOT_ALLOWED + responseStatus);
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_NOT_ACCEPTABLE) {
				// 406
				throw new ClientException(ERROR_NOT_ACCEPTABLE + responseStatus);
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
				// 422
				throw new ClientException(ERROR_CANNOT_PROCESS + responseStatus);
			} else if (responseStatus.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				// 500
				throw new ClientException(ERROR_INTERNAL_SERVER
						+ responseStatus);
			} else {
				throw new ClientException(ERROR_UNEXPECTED + responseStatus);
			}
		} catch (ClientException ce) {
			throw ce;
		} finally {
			response = null;
		}
	}

	/**
	 * Client response from a http request.
	 * 
	 * @param restAction
	 *            the rest action
	 * @param aLink
	 *            the a link
	 * 
	 * @return the http response
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	private static HttpResponse clientResponse(HttpRequestBase restAction,
			Link aLink) throws ClientException {

		restAction.setHeader(ACCEPT_HEADER, aLink.getMediaTypeString());

		// This is probably redundant, as Content-Type is also set in the string
		// representation.
		if (restAction.getMethod().equals("PUT")
				|| restAction.getMethod().equals("POST")) {
			restAction.setHeader(CONTENT_TYPE, aLink.getMediaTypeString());
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();

		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(aLink.getURIHost(), SSH_PORT),
				aLink.getAuthentication());

		if (HTTP_REQUEST_DEBUG) {
			dumpRequest(restAction);
			if (restAction.getMethod().equals("PUT")
					|| restAction.getMethod().equals("POST")) {
				dumpRequestEntity((HttpEntityEnclosingRequestBase) restAction);
			}

		}

		try {
			return httpClient.execute(restAction);
		} catch (ClientProtocolException e) {
			throw new ClientException(e.getMessage());
		} catch (IOException e) {
			throw new ClientException(e.getMessage());
		} finally {
			// ?? httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * Return a valid Representation of the response from the HTTP action.
	 * 
	 * @param response
	 *            the HTTP call response
	 * @param aLink
	 *            the link originating the response
	 * 
	 * @return a populated Representation subclass, as determined by the media
	 *         type.
	 * 
	 * @throws ClientException
	 *             the client exception
	 */

	private static Representation decodeResponse(HttpResponse response,
			Link aLink) throws ClientException {
		try {
			String mediaType = aLink.getMediaTypeString();

			Representation instanceRepresentation = LocalMediaType
					.createRepresentationInstance(mediaType);

			if (mediaType.equals("application/zip")) {

				HttpResponse r = clientResponse(new HttpGet(aLink.getURI()),
						aLink);

				HttpEntity returnedEntity = r.getEntity();

				((Zip) instanceRepresentation).setData(returnedEntity
						.getContent());

			} else if (mediaType.matches(".*json$")) {
				Link instanceLink = aLink;
				if (response.containsHeader("Location")) {
					Header location = response.getFirstHeader("Location");
					instanceLink = Link.create(mediaType, location.getValue(),
							aLink.getAuthentication());
				}

				String content = EntityUtils.toString(response.getEntity());
				JSONObject responseObject = new JSONObject(content);
				instanceRepresentation.populate(instanceLink, responseObject);
			} else {
				throw new ClientRuntimeException();
			}

			return instanceRepresentation;
		} catch (IOException ioException) {
			throw new ClientException(ERROR_ACCESS_RESPONSE
					+ ioException.getMessage());
		} catch (JSONException je) {
			throw new ClientRuntimeException(ERROR_NO_JSON_OBJECT_FROM_RESPONSE
					+ je.getMessage());
		}
	}

	private static void dumpRequest(HttpRequestBase request) {
		RequestLine rl = request.getRequestLine();
		System.out.print("URI: " + rl.getUri());
		System.out.print(" METHOD: " + rl.getMethod());
		System.out.println(" PROTOCOL: "
				+ rl.getProtocolVersion().getProtocol());
		System.out.println("***Headers***");
		Header[] heads = request.getAllHeaders();
		for (int hpt = 0; hpt < heads.length; hpt++) {
			System.out.println(heads[hpt].getName() + " : "
					+ heads[hpt].getValue());
		}
		System.out.println("***End Headers***");
	}

	private static void dumpRequestEntity(HttpEntityEnclosingRequestBase request) {
		System.out.println("***Entity***");
		try {
			request.getEntity().writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\n***End Entity***");
	}

}
