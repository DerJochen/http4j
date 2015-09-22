package de.jochor.lib.http4j;

import de.jochor.lib.http4j.apache.HTTPClientApacheCommens;

/**
 *
 * <p>
 * <b>Started:</b> 2015-09-03
 * </p>
 * @author Jochen Hormes
 * 
 */
public class StaticHTTPClientBinder {

	public static HTTPClient create() {
		HTTPClientApacheCommens httpClient = new HTTPClientApacheCommens();
		return httpClient;
	}

}
