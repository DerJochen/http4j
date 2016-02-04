package de.jochor.lib.http4j.junit;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jochor.lib.http4j.HTTPClientFactory;
import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;
import de.jochor.lib.servicefactory.ServiceFactory;

/**
 * Test for the jUnit implementation of the HTTP client.
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
	public void testDelete_noResponse() {
		DeleteRequest request = new DeleteRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(204);
		String content = httpClient.delete(request);
		Assert.assertNull(content);
	}

	@Test
	public void testDelete_withResponse() {
		String testContent = "{\"success\":true}";
		RequestHolder requestHolder = HTTPClientJUnit.addResponse(testContent);

		DeleteRequest request = new DeleteRequest(URI.create("http://localhost/"));
		String content = httpClient.delete(request);
		Assert.assertEquals(testContent, content);
		Assert.assertEquals(request, requestHolder.getRequest());
	}

	@Test
	public void testGet() {
		String testContent = "test";
		RequestHolder requestHolder = HTTPClientJUnit.addResponse(testContent);

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		String content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
		Assert.assertEquals(request, requestHolder.getRequest());
	}

	@Test
	public void testExpectedParameters_explicit() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent, "test1=dummy1", "test2=dummy2");

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(404);
		String content = httpClient.get(request);
		Assert.assertEquals("", content);

		request = new GetRequest(URI.create("http://localhost/"));
		request.setQueryParameter("test1", "dummy1");
		request.setQueryParameter("test2", "dummy2");
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testExpectedParameters_inURI() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent, "test1=dummy1", "test2=dummy2");

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(404);
		String content = httpClient.get(request);
		Assert.assertEquals("", content);

		request = new GetRequest(URI.create("http://localhost/?test1=dummy1&test2=dummy2"));
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testExpectedParameters_both() {
		String testContent = "test";
		HTTPClientJUnit.addResponse(testContent, "test1=dummy1", "test2=dummy2");

		GetRequest request = new GetRequest(URI.create("http://localhost/"));
		request.setExpectedStatus(404);
		String content = httpClient.get(request);
		Assert.assertEquals("", content);

		// In the URI both params are set to 'dummy1'.
		request = new GetRequest(URI.create("http://localhost/?test1=dummy1&test2=dummy1"));
		request.setExpectedStatus(404);
		content = httpClient.get(request);
		Assert.assertEquals("", content);

		// In the URI both params are set to 'dummy1'. This checks that the explicit params win.
		request = new GetRequest(URI.create("http://localhost/?test1=dummy1&test2=dummy1"));
		request.setQueryParameter("test1", "dummy1");
		request.setQueryParameter("test2", "dummy2");
		content = httpClient.get(request);
		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testPost() {
		String testContent = "test";
		RequestHolder requestHolder = HTTPClientJUnit.addResponse(testContent);

		PostRequest request = new PostRequest(URI.create("http://localhost/"), "{v=1}");
		String content = httpClient.post(request);
		Assert.assertEquals(testContent, content);
		Assert.assertEquals(request, requestHolder.getRequest());
	}

	@Test
	public void testPut() {
		String testContent = "test";
		RequestHolder requestHolder = HTTPClientJUnit.addResponse(testContent);

		PutRequest request = new PutRequest(URI.create("http://localhost/"), "{v=1}");
		String content = httpClient.put(request);
		Assert.assertEquals(testContent, content);
		Assert.assertEquals(request, requestHolder.getRequest());
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
