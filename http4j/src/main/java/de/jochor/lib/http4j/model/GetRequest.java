package de.jochor.lib.http4j.model;

import java.net.URI;

/**
 * Represents a HTTP GET request.
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class GetRequest extends BaseRequest {

	/**
	 * Creates a new HTTP GET request.
	 *
	 * @param uri
	 *            Address to request
	 */
	public GetRequest(URI uri) {
		super(uri);
	}

}
