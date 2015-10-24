package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;

/**
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@Getter
public class PutRequest extends BaseRequest {

	/**
	 * Creates a new HTTP PUT request.
	 * 
	 * @param uri
	 *            Address to request
	 * @param body
	 *            Body of the request
	 */
	public PutRequest(URI uri, String body) {
		super(uri);
		this.body = body;
	}

	private String body;

}
