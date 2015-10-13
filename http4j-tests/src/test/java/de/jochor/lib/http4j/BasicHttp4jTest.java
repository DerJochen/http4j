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
import de.jochor.lib.http4j.model.GetRequest;
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
	public void testSimpleGet() {
		String testContent = "test content";

		new MockServerClient("localhost", freePort) //
		.when(HttpRequest.request("/").withMethod("GET")) //
		.respond(HttpResponse.response(testContent));

		GetRequest request = new GetRequest(URI.create("http://localhost:" + freePort + "/"));

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	private static int pickFreePort() {
		try (ServerSocket socket = new ServerSocket(0)) {
			return socket.getLocalPort();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
