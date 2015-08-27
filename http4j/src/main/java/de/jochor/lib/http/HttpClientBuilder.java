package de.jochor.lib.http;

import de.jochor.lib.servicebuilder.ServiceBuilder;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public class HttpClientBuilder extends ServiceBuilder {

	public static final String CLASS_NAME_PROPERTY = "jochor.http.class-name";

	private static final Class<HttpClient> SERVICE_TYPE = HttpClient.class;

	private static Class<? extends HttpClient> serviceClass;

	static {
		serviceClass = findImplementation(SERVICE_TYPE, CLASS_NAME_PROPERTY);
	}

	public static HttpClient create() {
		HttpClient httpClient = create(serviceClass);
		return httpClient;
	}

}
