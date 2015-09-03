package de.jochor.lib.http4j;

import de.jochor.lib.servicebuilder.ServiceFactory;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public class HttpClientFactory extends ServiceFactory {

	private static final String BINDER_NAME = "de/jochor/lib/http4j/StaticHttpClientBinder.class";

	public static HttpClient create() {
		HttpClient httpClient = create(BINDER_NAME);
		return httpClient;
	}

}
