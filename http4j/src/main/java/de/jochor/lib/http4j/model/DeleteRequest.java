package de.jochor.lib.http4j.model;

import java.net.URI;

/**
 *
 * <p>
 * <b>Started:</b> 2015-10-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class DeleteRequest extends BaseRequest {

	/**
	 * Creates a new HTTP DELETE request.
	 *
	 * @param uri
	 *            Address to request
	 */
	public DeleteRequest(URI uri) {
		super(uri);
	}

}
