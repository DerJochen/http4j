package de.jochor.lib.http4j.junit;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jochor.lib.http4j.HTTPClientFactory;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;
import de.jochor.lib.servicefactory.ServiceFactory;

/**
 * Test for HTTP client adapter implementations.
 *
 * <p>
 * <b>Started:</b> 2015-10-15
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPClientJUnitTest {

	private HTTPClientJUnit httpClient;

	@BeforeClass
	public static void setUpBeforeClass() {
		// Switch off outputs from the service factory
		System.setProperty(ServiceFactory.SILENT_MODE, "true");
	}

	@Before
	public void setUp() throws Exception {
		httpClient = (HTTPClientJUnit) HTTPClientFactory.create();

		HTTPClientJUnit.clearExpectedHeaders();
		HTTPClientJUnit.clearResponses();
	}

	@Test
	public void testInstantiation() {
		Assert.assertNotNull(httpClient);
	}

	@Test
	public void testGet() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		String content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testGetWithParams() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent, "test=1");

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(404);
		String content = httpClient.get(request);
		Assert.assertEquals("", content);

		request = new GetRequest(URI.create("http://localhost/?test=1"));
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testPost() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);

		PostRequest request = new PostRequest(URI.create("http://localhost/"), "{v=1}");
		String content = httpClient.post(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testPut() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);

		PutRequest request = new PutRequest(URI.create("http://localhost/"), "{v=1}");
		String content = httpClient.put(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testAddExpectedHeader() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);
		HTTPClientJUnit.addResponse(testContent);

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		String content = httpClient.get(request);
		Assert.assertEquals(testContent, content);

		HTTPClientJUnit.addExpectedHeader("x-test", "value");

		request.setExpectedStatus(404);
		content = httpClient.get(request);
		Assert.assertEquals("", content);

		request.setExpectedStatus(200);
		request.setHeader("x-test", "value");
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testClearExpectedHeaders() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);
		HTTPClientJUnit.addResponse(testContent);
		HTTPClientJUnit.addExpectedHeader("x-test", "value");

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(404);
		String content = httpClient.get(request);
		Assert.assertEquals("", content);

		HTTPClientJUnit.clearExpectedHeaders();
		request.setExpectedStatus(200);
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test(expected = IllegalStateException.class)
	public void testClearResponses() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent);
		HTTPClientJUnit.addResponse(testContent);

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		httpClient.get(request);

		HTTPClientJUnit.clearResponses();

		httpClient.get(request);
	}

}
