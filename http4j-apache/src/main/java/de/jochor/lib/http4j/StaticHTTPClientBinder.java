package de.jochor.lib.http4j;

import de.jochor.lib.http4j.apache.HTTPClientApache;

/**
 * Static binder for the Apache HTTP Components adapter implementation of the {@link HTTPClient}.
 *
 * <p>
 * <b>Started:</b> 2015-09-03
 * </p>
 * 
 * @author Jochen Hormes
 *
 */
public class StaticHTTPClientBinder {

	public static HTTPClient create() {
		HTTPClientApache httpClient = new HTTPClientApache();
		return httpClient;
	}

}
