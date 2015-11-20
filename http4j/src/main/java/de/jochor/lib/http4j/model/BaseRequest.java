package de.jochor.lib.http4j.model;

import java.net.URI;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Base class for HTTP request.
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

	@Setter(AccessLevel.NONE)
	private HashMap<String, String> headers;

	@Setter(AccessLevel.NONE)
	private HashMap<String, String> queryParameters;

	private int expectedStatus = 200;

	/**
	 * Sets a header value. If the header already exists the old value is overwritten.
	 *
	 * @param name
	 *            Name of the header
	 * @param value
	 *            Value to set
	 */
	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<>();
		}

		headers.put(name, value);
	}

	/**
	 * Sets a query parameter. If the query parameter already exists the old value is overwritten.
	 *
	 * @param name
	 *            Name of the query parameter
	 * @param value
	 *            value to set
	 */
	public void setQueryParameter(String name, String value) {
		if (queryParameters == null) {
			queryParameters = new HashMap<>();
		}

		queryParameters.put(name, value);
	}

	/**
	 * Sets a query parameter. If the query parameter already exists the old value is overwritten.
	 *
	 * @param name
	 *            Name of the query parameter
	 * @param value
	 *            value to set
	 */
	public void setQueryParameter(String name, int value) {
		String valueString = Integer.toString(value);
		setQueryParameter(name, valueString);
	}
	
}
