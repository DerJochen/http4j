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
public class PostRequest extends BaseRequest {

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

	private String body;

}
