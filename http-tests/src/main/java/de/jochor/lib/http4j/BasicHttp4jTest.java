package de.jochor.lib.http4j;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for JSON Binding Service adapter implementations.
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

	@Test
	public void test() {
		// TODO
	}

}
