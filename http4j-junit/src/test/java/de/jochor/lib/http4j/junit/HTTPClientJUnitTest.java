package de.jochor.lib.http4j.junit;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.jochor.lib.http4j.HTTPClientFactory;
import de.jochor.lib.servicefactory.ServiceFactory;

// TODO Implement test for HTTPClientJUnit
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
	}

	@Ignore
	@Test
	public void testAddExpectedHeader() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testClearExpectedHeaders() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testAddResponse() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testPost() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testPut() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testExecuteRequest() {
		fail("Not yet implemented");
	}

}
