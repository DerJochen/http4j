package de.jochor.lib.http4j;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jochor.lib.servicefactory.ServiceFactory;

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
		// Switch off outputs from the service factory
		System.setProperty(ServiceFactory.SILENT_MODE, "true");
	}

	@Test
	public void testCreate() {
		HTTPClient httpClient = HTTPClientFactory.create();
		Assert.assertNotNull(httpClient);
	}

}
