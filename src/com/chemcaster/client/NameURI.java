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
/**
 * The Class NameURI.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

public class NameURI {

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> uris = new ArrayList<String>();

	/**
	 * Adds the correlated name, uri pair to the lists.
	 * 
	 * @param name the name
	 * @param uri the uri
	 */
	public void add(String name, String uri) {
		names.add(name);
		uris.add(uri);
	}

	/**
	 * Gets the ith name.
	 * 
	 * @param i the position
	 * 
	 * @return the name
	 */
	public String getName(Integer i) {
		return names.get(i);
	}

	/**
	 * Gets the ith URI.
	 * 
	 * @param i the position
	 * 
	 * @return the URI
	 */
	public String getURI(Integer i) {
		return uris.get(i);
	}
	
	/**
	 * number of name URI pairs
	 * 
	 * @return the integer
	 */
	public int size() {
		return names.size();
	}
}
