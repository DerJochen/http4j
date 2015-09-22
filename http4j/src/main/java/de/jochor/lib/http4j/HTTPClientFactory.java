package de.jochor.lib.http4j;

import de.jochor.lib.servicefactory.ServiceFactory;

/**
 * Factory for HTTP Client adapters. Use the static {@link #create()} method to retrieve an instance.
 * 
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public class HTTPClientFactory extends ServiceFactory {

	private static final String BINDER_NAME = "de/jochor/lib/http4j/StaticHTTPClientBinder.class";

	/**
	 * Factory method to produce a new instance of a HTTP Client adapter.
	 *
	 * @return Service instance
	 */
	public static HTTPClient create() {
		HTTPClient httpClient = create(BINDER_NAME);
		return httpClient;
	}

}
