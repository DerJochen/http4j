package de.jochor.lib.http4j.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Content type for HTTP requests.
 *
 * <p>
 * <b>Started:</b> 2015-11-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
@RequiredArgsConstructor
@Getter
public enum ContentType {

	APPLICATION_JSON("application/json"), //
	TEXT_HTML("text/html"), //
	TEXT_PLAIN("text/plain");

	private final String mimeType;

}
