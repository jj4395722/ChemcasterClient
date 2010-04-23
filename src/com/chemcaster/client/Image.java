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
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class Image. An Image represents an image dynamically generated for a
 * Structure, Substance, or Query. Raw image binary data are encoded using
 * Base-64 encoding (RFC3548). Only 'image/png' is currently supported by
 * Chemcaster. The media type is [application/vnd.com.chemcaster.Image+json]
 * 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */
public class Image extends Item {
	public static final String IMAGE_MEDIA_TYPE = "application/vnd.com.chemcaster.Image+json";

	private static final String DATA_TAG = "data";
	private static final String FORMAT_TAG = "format";
	private static final String HEIGHT_TAG = "height";
	private static final String IMAGEABLE_TAG = "imageable";
	private static final String WIDTH_TAG = "width";

	private String[] NEW_ATTRIBUTES = { WIDTH_TAG, HEIGHT_TAG, DATA_TAG,
			FORMAT_TAG };
	private String[] NEW_RESOURCES = { IMAGEABLE_TAG };

	/**
	 * Instantiates a new image.
	 */
	public Image() {
		super();
		addNewAttributeList(NEW_ATTRIBUTES);
		addNewResourceList(NEW_RESOURCES);
	}

	/**
	 * Gets the width of this Image in image units.
	 * 
	 * @return the width
	 */
	public Number getWidth() {
		return getAttribute(WIDTH_TAG);
	}

	/**
	 * Gets the height of this Image in image units.
	 * 
	 * @return the height
	 */
	public Number getHeight() {
		return getAttribute(HEIGHT_TAG);
	}

	/**
	 * Gets the raw image data. Binary data are encoded using Base-64 encoding
	 * (RFC3548).
	 * 
	 * @return the raw image data
	 */
	public String getData() {
		return getAttribute(DATA_TAG);
	}

	/**
	 * Gets the image as a BufferedImage.
	 * 
	 * @return the image, or null if the conversion failed.
	 */
	public BufferedImage getImage() {
		String base64ImageString = getData();
		ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64
				.decodeBase64(base64ImageString.getBytes()));
		try {
			return ImageIO.read(byteStream);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the format. Note: only 'image/png' is currently supported by
	 * Chemcaster.
	 * 
	 * @return the format
	 */
	public String getFormat() {
		return getAttribute(FORMAT_TAG);
	}

	/**
	 * Gets the imageable link.
	 * 
	 * @return the imageable link
	 * 
	 * @throws ClientException
	 *             the client exception
	 */
	public Link getImageableLink() throws ClientException {
		return linkFromResourceBlock(IMAGEABLE_TAG);
	}

	/**
	 * Returns null, as there is no Index resource for Image
	 */
	public Link getIndexLink() {
		return null;
	}
}
