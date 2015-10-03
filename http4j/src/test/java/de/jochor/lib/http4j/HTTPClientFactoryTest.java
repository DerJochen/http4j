package de.jochor.lib.http4j;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * <b>Started:</b> 2015-10-03
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPClientFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		System.setProperty("jochor.servicefactory.silence", "true");
	}

	@Test
	public void testCreate() {
		HTTPClient httpClient = HTTPClientFactory.create();
		Assert.assertNotNull(httpClient);
	}

}
