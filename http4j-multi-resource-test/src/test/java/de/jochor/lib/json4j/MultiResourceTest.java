package de.jochor.lib.json4j;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jochor.lib.http4j.HTTPClientFactory;
import de.jochor.lib.http4j.StaticHTTPClientBinder;
import de.jochor.lib.http4j.apache.HTTPClientApache;
import de.jochor.lib.http4j.junit.HTTPClientJUnit;
import de.jochor.lib.servicefactory.ServiceFactory;

/**
 * Test for the behavior of the {@link ServiceFactory} when finding the same static binder in multiple sources.
 *
 * <p>
 * <b>Started:</b> 2015-11-15
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class MultiResourceTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		// Switch off outputs from the service factory
		System.setProperty(ServiceFactory.SILENT_MODE, "true");
	}

	@Test
	public void testCreate_default() throws Throwable {
		Object service = HTTPClientFactory.create();
		Assert.assertNotNull(service);
		Assert.assertTrue(service instanceof HTTPClientApache || service instanceof HTTPClientJUnit);
	}

	@Test
	public void testCreate_Gson() throws Throwable {
		System.setProperty(ServiceFactory.PROPERTIES_BASE + StaticHTTPClientBinder.class.getName(), HTTPClientApache.class.getName());

		Object service = HTTPClientFactory.create();
		Assert.assertNotNull(service);
		Assert.assertTrue(service instanceof HTTPClientApache);
	}

	@Test
	public void testCreate_Jackson() throws Throwable {
		System.setProperty(ServiceFactory.PROPERTIES_BASE + StaticHTTPClientBinder.class.getName(), HTTPClientJUnit.class.getName());

		Object service = HTTPClientFactory.create();
		Assert.assertNotNull(service);
		Assert.assertTrue(service instanceof HTTPClientJUnit);
	}

}