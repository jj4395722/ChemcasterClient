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
 * The Class SimpleComponent.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleComponent {

	Component component;

	/**
	 * Instantiates a new SimpleComponent.
	 * 
	 * @param newComponent the Component instance
	 */
	public SimpleComponent(Component newComponent) {
		component = newComponent;
	}

	/**
	 * Instantiates a new SimpleComponent from a URI.
	 * 
	 * @param componentURI the component URI
	 * @param username the username
	 * @param password the password
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleComponent(String componentURI, String username, String password)
			throws ClientException {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(
				username, password);
		Link cLink = Link.create(Component.COMPONENT_MEDIA_TYPE,
				componentURI, cred);
		component = ClientHttp.get(cLink);
	}
	
	/**
	 * Gets the Component.
	 * 
	 * @return the Component
	 */	
	public Component getComponent() {
		return component;
	}

	/**
	 * Gets the multiplier.
	 * 
	 * @return the multiplier
	 */
	public Number getMultiplier() {
		return component.getMultiplier();
	}

	/**
	 * Gets the SimpleStructure.
	 * 
	 * @return the SimpleStructure
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleStructure getSimpleStructure() throws ClientException {
		Structure structure = ClientHttp.get(component.getStructureLink());
		return new SimpleStructure(structure);
	}

	/**
	 * Gets the SimpleSubstance.
	 * 
	 * @return the SimpleSubstance
	 * 
	 * @throws ClientException the client exception
	 */
	public SimpleSubstance getSimpleSubstance() throws ClientException {
		Substance substance = ClientHttp.get(component.getSubstanceLink());
		return new SimpleSubstance(substance);
	}
	
	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = component.getLinkToSelf();
		return l.getURI();
	}

}
