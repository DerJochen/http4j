package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for HTTP request with content.
 *
 * <p>
 * <b>Started:</b> 2015-11-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@Getter
public abstract class BaseContentRequest extends BaseRequest {

	private String body;

	@Setter
	private ContentType contentType = ContentType.APPLICATION_JSON;

	protected BaseContentRequest(URI uri, String body) {
		super(uri);

		this.body = body;
	}

}
