package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.ContentType;

/**
 * Exception thrown in adapter implementations when encountering an unknown {@link ContentType}.
 *
 * <p>
 * <b>Started:</b> 2015-11-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class UnknownContentTypeException extends RuntimeException {

	private static final long serialVersionUID = 782405344919246177L;

	/**
	 * Creates a new {@link UnknownContentTypeException}.
	 * 
	 * @param contentType
	 *            Unknown {@link ContentType}
	 */
	public UnknownContentTypeException(ContentType contentType) {
		super("Unknown content type: " + contentType.name());
	}

}
