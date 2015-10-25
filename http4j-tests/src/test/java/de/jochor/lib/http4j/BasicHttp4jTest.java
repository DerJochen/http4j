package de.jochor.lib.http4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.mockserver.MockServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;
import de.jochor.lib.servicefactory.ServiceFactory;

/**
 * Test for HTTP client adapter implementations.
 *
 * <p>
 * <b>Started:</b> 2015-10-03
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public abstract class BasicHttp4jTest {

	private HTTPClient httpClient;

	private int freePort;

	private MockServer mockServer;

	@BeforeClass
	public static void setUpBeforeClass() {
		// Switch off outputs from the service factory
		System.setProperty(ServiceFactory.SILENT_MODE, "true");

		// Switch off outputs from MockServer
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.WARN);
	}

	@Before
	public void setUp() throws Exception {
		httpClient = HTTPClientFactory.create();

		freePort = pickFreePort();
		mockServer = new MockServer(freePort);
	}

	@After
	public void tearDown() {
		mockServer.stop();
	}

	@Test
	public void testInstantiation() {
		Assert.assertNotNull(httpClient);
	}

	@Test
	public void testSimpleDelete() {
		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("DELETE")) //
		.respond(HttpResponse.response().withStatusCode(204));

		DeleteRequest request = new DeleteRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setExpectedStatus(204);

		String content = httpClient.delete(request);

		Assert.assertNull(content);
	}

	@Test
	public void testSimpleGet() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testSimpleGet2() {
		String testContent = "test content\\nsecond line";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testSimplePost() {
		String testContent = "test content";
		String testBody = "test body";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("POST")) //
		.respond(HttpResponse.response(testContent + " - " + testBody));

		PostRequest request = new PostRequest(URI.create("http://localhost:" + freePort + "/"), testBody);

		String content = httpClient.post(request);

		Assert.assertEquals(testContent + " - " + testBody, content);
	}

	@Test
	public void testSimplePut() {
		String testContent = "test content";
		String testBody = "test body";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("PUT")) //
		.respond(HttpResponse.response(testContent + " - " + testBody));

		PutRequest request = new PutRequest(URI.create("http://localhost:" + freePort + "/"), testBody);

		String content = httpClient.put(request);

		Assert.assertEquals(testContent + " - " + testBody, content);
	}

	@Test
	public void testWrongMethod() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("PUT")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setExpectedStatus(404); // wrong method, so nothing should be found

		String content = httpClient.get(request);

		Assert.assertEquals("", content);
	}

	@Test
	public void testExpectedParameters() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET").withQueryStringParameter("test1", "dummy1").withQueryStringParameter("test2", "dummy2")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setQueryParameter("test1", "dummy1");
		request.setQueryParameter("test2", "dummy2");

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testExpectedParametersMissing() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET").withQueryStringParameter("x-test1", "dummy1").withQueryStringParameter("x-test2", "dummy2")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setQueryParameter("test1", "dummy1");
		request.setExpectedStatus(404); // header missing, so nothing should be found

		String content = httpClient.get(request);

		Assert.assertEquals("", content);
	}

	@Test
	public void testExpectedHeaders() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET").withHeader("x-test1", "dummy1").withHeader("x-test2", "dummy2")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setHeader("x-test1", "dummy1");
		request.setHeader("x-test2", "dummy2");

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	@Test
	public void testExpectedHeadersMissing() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET").withHeader("x-test1", "dummy1").withHeader("x-test2", "dummy2")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));
		request.setHeader("x-test1", "dummy1");
		request.setExpectedStatus(404); // header missing, so nothing should be found

		String content = httpClient.get(request);

		Assert.assertEquals("", content);
	}

	private static int pickFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			return socket.getLocalPort();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
