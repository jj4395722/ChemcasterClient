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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.Image;
import com.chemcaster.client.Link;

public class MockImageTest {
/* 
 * @author John Jaeger <jj4395722_at_yahoo_dot_com>
 */

	public final static String WIDTH = "width";
	public static final Number WIDTH_RESPONSE = 200;

	public final static String HEIGHT = "height";
	public static final Number HEIGHT_RESPONSE = 300;

	public final static String FORMAT = "format";
	public final static String FORMAT_RESPONSE = "image/png";

	public final static String DATA = "data";

	public final static String URI = "uri";
	public final static String URI_RESPONSE = "http://uri";

	public final static String MEDIA_TYPE = "media_type";
	public final static String MEDIA_TYPE_RESPONSE = "x";

	public final static String NAME = "name";
	public final static String NAME_RESPONSE = "someName";

	static BufferedImage bufferedImage;
	static String encodedImage;

	UsernamePasswordCredentials cred;
	Link someLink;
	JSONObject response;
	JSONObject genericLink;
	MockUtilities m = new MockUtilities();

	// create an image for testing - once
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.setColor(Color.BLUE);
		g.drawString("Biffo", 500, 500);
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", byteOutputStream);
		byteOutputStream.flush();
		byte[] imageAsRawBytes = byteOutputStream.toByteArray();
		byteOutputStream.close();
		encodedImage = new String(Base64.encodeBase64(imageAsRawBytes));
	}

	@Before
	public void setUp() throws Exception {
		cred = mock(UsernamePasswordCredentials.class);
		someLink = mock(Link.class);
		when(someLink.getAuthentication()).thenReturn(cred);
		response = mock(JSONObject.class);

		JSONObject imageResponse = mock(JSONObject.class);

		try {
			m.hasAndReturns(imageResponse, WIDTH, WIDTH_RESPONSE);
			m.hasAndReturns(imageResponse, HEIGHT, HEIGHT_RESPONSE);
			m.hasAndReturns(imageResponse, FORMAT, FORMAT_RESPONSE);
			m.hasAndReturns(imageResponse, DATA, encodedImage);
			m.hasAndReturns(response, "image", imageResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		genericLink = mock(JSONObject.class);

		try {
			m.hasAndReturnsString(genericLink, NAME, NAME_RESPONSE);
			m.hasAndReturnsString(genericLink, URI, URI_RESPONSE);
			m.hasAndReturnsString(genericLink, MEDIA_TYPE, MEDIA_TYPE_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * there is no Index link defined for Image, so it should always return null
	 */
	@Test
	public final void testGetIndexLink() {
		Image img = new Image();
		img.populate(someLink, response);
		assertNull(img.getIndexLink());
	}

	@Test
	public final void testImage() {
		Image img = new Image();
		assertNotNull(img);
	}

	@Test
	public final void testGetWidth() {
		Image img = new Image();
		img.populate(someLink, response);
		assertEquals(img.getWidth(), WIDTH_RESPONSE);
	}

	@Test
	public final void testGetHeight() {
		Image img = new Image();
		img.populate(someLink, response);
		assertEquals(img.getHeight(), HEIGHT_RESPONSE);
	}

	@Test
	public final void testGetData() {
		Image img = new Image();
		img.populate(someLink, response);
		assertEquals(img.getData(), encodedImage);
	}

	@Test
	public final void testGetImage() {
		Image img = new Image();
		img.populate(someLink, response);
		BufferedImage imgGet = img.getImage();
		assertNotNull(imgGet);
		assertEquals(imgGet.getHeight(), bufferedImage.getHeight());
		assertEquals(imgGet.getWidth(), bufferedImage.getWidth());
	}

	@Test
	public final void testGetFormat() {
		Image img = new Image();
		img.populate(someLink, response);
		assertEquals(img.getFormat(), FORMAT_RESPONSE);
	}

	@Test
	public final void testGetImageableLink() throws ClientException {
		try {
			m.hasAndReturns(response, "imageable", genericLink);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Image img = new Image();
		img.populate(someLink, response);
		Link link = img.getImageableLink();
		assertNotNull(link);
		assertEquals(link.getURI(), URI_RESPONSE);
		assertEquals(link.getName(), NAME_RESPONSE);
		assertEquals(link.getMediaTypeString(), MEDIA_TYPE_RESPONSE);
		assertEquals(link.getAuthentication(), cred);
	}

}
