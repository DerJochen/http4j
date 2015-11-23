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

	private StaticHTTPClientBinder() {
		// Intended blank
	}

	/**
	 * Factory method to produce a new instance of a {@link HTTPClientApache}.
	 *
	 * @return Service instance
	 */
	public static HTTPClient create() {
		HTTPClientApache httpClient = new HTTPClientApache();
		return httpClient;
	}

	/**
	 * Returns the full qualified name of the service class this binder creates.
	 * 
	 * @return Name of the service
	 */
	public static String getImplName() {
		return HTTPClientApache.class.getName();
	}

}
