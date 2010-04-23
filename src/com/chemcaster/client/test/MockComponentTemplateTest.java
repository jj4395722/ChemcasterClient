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
package com.chemcaster.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemcaster.client.ComponentTemplate;

public class MockComponentTemplateTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

	public final static String MULTIPLIER = "multiplier";
	public static final Double MULTIPLIER_RESPONSE = 1.0;

	public final static String SERIALIZATION = "serialization";
	public final static String SERIALIZATION_RESPONSE = "molfile";

	JSONObject component;

	@Before
	public void setUp() throws Exception {
		component = mock(JSONObject.class);
		when(component.has(MULTIPLIER)).thenReturn(true);
		when(component.has(SERIALIZATION)).thenReturn(true);
		try {
			when(component.getDouble(MULTIPLIER)).thenReturn(
					MULTIPLIER_RESPONSE);
			when(component.getString(SERIALIZATION)).thenReturn(
					SERIALIZATION_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testComponentTemplate() {
		ComponentTemplate ct = new ComponentTemplate(component);
		assertNotNull(ct);
	}

	@Test
	public final void testGetMultiplier() {
		ComponentTemplate ct = new ComponentTemplate(component);
		assertEquals(ct.getMultiplier(), MULTIPLIER_RESPONSE);
	}

	@Test
	public final void testGetSerialization() {
		ComponentTemplate ct = new ComponentTemplate(component);
		assertEquals(ct.getSerialization(), SERIALIZATION_RESPONSE);
	}

}
