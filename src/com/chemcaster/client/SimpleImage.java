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

import java.awt.image.BufferedImage;

/**
 * The Class SimpleImage.
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class SimpleImage {

	Image image;

	/**
	 * Instantiates a new SimpleImage.
	 * 
	 * @param newImage
	 *            the new image
	 */
	public SimpleImage(Image newImage) {
		image = newImage;
	}

	// This returns a 406, so it isn't a valid operation.
	// public SimpleImage(String imageURI, String username, String password)
	// throws ClientException {

	/**
	 * Instantiates a new SimpleImage from an imageLink and image params.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param format
	 *            the format
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleImage(Link imageLink, Number width, Number height,
			String format) throws ClientException {
		AttributeHash imgAttribs = new AttributeHash();
		imgAttribs.put("width", width);
		imgAttribs.put("height", height);
		imgAttribs.put("format", format);

		Index imgIndex = ClientHttp.get(imageLink);
		image = ClientHttp.post(imgIndex.create(), imgAttribs);
	}

	/**
	 * Gets the URI.
	 * 
	 * @return the URI
	 */
	public String getURI() {
		Link l = image.getLinkToSelf();
		return l.getURI();
	}

	/**
	 * Gets the Image object.
	 * 
	 * @return the Image
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Gets the buffered image.
	 * 
	 * @return the Buffered Image
	 */
	public BufferedImage getBufferedImage() {
		return image.getImage();
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public Number getWidth() {
		return image.getWidth();
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public Number getHeight() {
		return image.getHeight();
	}

	/**
	 * Gets the format.
	 * 
	 * @return the format
	 */
	public String getFormat() {
		return image.getFormat();
	}

	/**
	 * If this image was created from a Structure, get the SimpleStructure
	 * 
	 * @return the SimpleStructure
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleStructure getSimpleStructure() throws ClientException {
		Link imgLink = image.getImageableLink();
		if (imgLink.getName().equals("structure")) {
			Structure str = ClientHttp.get(image.getImageableLink());
			return new SimpleStructure(str);
		} else {
			return null;
		}
	}

	/**
	 * If this image was created from a Query, get the SimpleQuery
	 * 
	 * @return the SimpleQuery
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleQuery getSimpleQuery() throws ClientException {
		Link imgLink = image.getImageableLink();
		if (imgLink.getName().equals("query")) {
			Query query = ClientHttp.get(image.getImageableLink());
			return new SimpleQuery(query);
		} else {
			return null;
		}
	}

	/**
	 * If this image was created from a Substance, get the SimpleSubstance
	 * 
	 * @return the SimpleSubstance
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public SimpleSubstance getSimpleSubstance() throws ClientException {
		Link imgLink = image.getImageableLink();
		if (imgLink.getName().equals("substance")) {
			Substance sub = ClientHttp.get(image.getImageableLink());
			return new SimpleSubstance(sub);
		} else {
			return null;
		}
	}
}
