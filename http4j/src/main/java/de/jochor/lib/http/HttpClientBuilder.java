package de.jochor.lib.http;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public class HttpClientBuilder {

	// TODO find with classpath scanner
	private static final String HTTP_CLIENT_CLASS_NAME = "de.jochor.lib.http.apache.HttpClientApacheCommens";

	private static Class<? extends HttpClient> httpClientClass;

	static {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			httpClientClass = classLoader.loadClass(HTTP_CLIENT_CLASS_NAME).asSubclass(HttpClient.class);
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to find " + HttpClient.class.getSimpleName() + " implementation");
			// TODO throw new RuntimeException(e);
		}
	}

	public static HttpClient create() {
		try {
			HttpClient httpClient = httpClientClass.newInstance();
			return httpClient;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
