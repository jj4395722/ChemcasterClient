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

import org.json.JSONException;
import org.json.JSONObject;
/**
 * The Class ComponentTemplate. A ComponentTemplate is a JSON data structure
 * used to specify a Component to be created by a Registration. It is exclusively
 * used by Registration.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class ComponentTemplate {
	JSONObject aTemplate;

	/**
	 * Instantiates a new component template.
	 * 
	 * @param jo
	 *            a JSON Object represented by the Component Template
	 */
	public ComponentTemplate(JSONObject jo) {
		aTemplate = jo;
	}

	/**
	 * Gets the multiplier.
	 * 
	 * @return the multiplier
	 */
	public Number getMultiplier() {
		try {
			return aTemplate.getDouble("multiplier");
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Gets the serialization.
	 * 
	 * @return the serialization, typically in molfile format
	 */
	public String getSerialization() {
		try {
			return aTemplate.getString("serialization");
		} catch (JSONException e) {
			return null;
		}
	}

}
