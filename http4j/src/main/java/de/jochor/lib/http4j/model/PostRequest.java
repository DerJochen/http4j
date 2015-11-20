package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;

/**
 * Represents a HTTP POST request.
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@Getter
public class PostRequest extends BaseRequest {

	private String body;

	/**
	 * Creates a new HTTP POST request.
	 *
	 * @param uri
	 *            Address to request
	 * @param body
	 *            Body of the request
	 */
	public PostRequest(URI uri, String body) {
		super(uri);
		this.body = body;
	}

}
