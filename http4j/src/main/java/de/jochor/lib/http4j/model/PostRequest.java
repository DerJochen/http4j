package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;
import lombok.Setter;

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
@Setter
public class PostRequest extends BaseRequest {

	public PostRequest(URI uri) {
		super(uri);
	}

	private String body;

}
