package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;

/**
 * Represents a HTTP PATCH request.
 *
 * <p>
 * <b>Started:</b> 2016-04-10
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@Getter
public class PatchRequest extends BaseContentRequest {

	/**
	 * Creates a new HTTP PATCH request.
	 *
	 * @param uri
	 *            Address to request
	 * @param body
	 *            Body of the request
	 */
	public PatchRequest(URI uri, String body) {
		super(uri, body);
	}

}
