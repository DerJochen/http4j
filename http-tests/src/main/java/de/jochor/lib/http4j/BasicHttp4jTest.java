package de.jochor.lib.http4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import de.jochor.lib.http4j.model.GetRequest;

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
public class BasicHttp4jTest {

	private HTTPClient httpClient;

	@BeforeClass
	public static void setUpBeforeClass() {
		System.setProperty("jochor.servicefactory.silence", "true");
	}

	@Before
	public void setUp() throws Exception {
		httpClient = HTTPClientFactory.create();
	}

	@Test
	public void testInstantiation() {
		Assert.assertNotNull(httpClient);
	}

	@Ignore
	@Test
	public void testSimpleGet() {
		String testContent = "test content";
		// MockServer mockServer = new MockServer(8080);
		MockServerClient mockServerClient = new MockServerClient("localhost", 8080);
		mockServerClient.when(new HttpRequest().withMethod("GET").withPath("/")).respond(new HttpResponse().withBody(testContent));

		GetRequest request = new GetRequest(URI.create("TODO"));

		String content = httpClient.get(request);

		Assert.assertEquals(testContent, content);
	}

	private static int pickOpenPort() {
		try (final ServerSocket socket = new ServerSocket(0)) {
			return socket.getLocalPort();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
