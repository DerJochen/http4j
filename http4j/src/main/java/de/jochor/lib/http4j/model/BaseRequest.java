package de.jochor.lib.http4j.model;

import java.net.URI;
import java.util.HashMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * <p>
 * <b>Started:</b> 2015-09-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@Getter
@Setter
@RequiredArgsConstructor
public abstract class BaseRequest {

	private final URI uri;

	@Getter
	@Setter
	private HashMap<String, String> headers;

	private int expectedStatus = 200;

	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<>();
		}

		headers.put(name, value);
	}
}
