package de.jochor.lib.http4j;

import de.jochor.lib.http4j.junit.HTTPClientJUnit;

/**
 * Static binder for the jUnit implementation of the {@link HTTPClient}.
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
		HTTPClientJUnit httpClient = new HTTPClientJUnit();
		return httpClient;
	}

	public static String getImplName() {
		return HTTPClientJUnit.class.getName();
	}

}
