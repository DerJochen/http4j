package de.jochor.lib.http4j.model;

import java.net.URI;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for request entity implementations.
 *
 * <p>
 * <b>Started:</b> 2015-11-27
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class PostRequestTest {

	@Test
	public void testGetContentType() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		Assert.assertEquals(ContentType.APPLICATION_JSON, request.getContentType());
	}

	@Test
	public void testSetContentType() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		request.setContentType(null);
		Assert.assertNull(request.getContentType());
	}

	@Test
	public void testSetHeader() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		request.setHeader("x-test", "testing");
		request.setHeader("x-test2", "testing2");

		HashMap<String, String> headers = request.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertEquals(2, headers.size());
		Assert.assertEquals("testing", headers.get("x-test"));
		Assert.assertEquals("testing2", headers.get("x-test2"));
	}

	@Test
	public void testGetQueryParameters() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		Assert.assertNull(request.getQueryParameters());

		request.setQueryParameter("x", "testing");
		Assert.assertNotNull(request.getQueryParameters());
	}

	@Test
	public void testSetQueryParameter_String() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		request.setQueryParameter("x", "testing");
		request.setQueryParameter("x", "testing2");

		HashMap<String, String> queryParameters = request.getQueryParameters();
		Assert.assertNotNull(queryParameters);
		Assert.assertEquals("testing2", queryParameters.get("x"));
	}

	@Test
	public void testSetQueryParameter_int() {
		PostRequest request = new PostRequest(URI.create("http://localhost/"), "request body");
		request.setQueryParameter("x1", 4);
		request.setQueryParameter("x2", 8);

		HashMap<String, String> queryParameters = request.getQueryParameters();
		Assert.assertNotNull(queryParameters);
		Assert.assertEquals("4", queryParameters.get("x1"));
		Assert.assertEquals("8", queryParameters.get("x2"));
	}

}
