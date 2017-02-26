package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.ContentType;
import lombok.Getter;

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

	@Getter
	private final Exception original;

	/**
	 * Creates a new {@link UnknownContentTypeException}.
	 *
	 * @param contentType
	 *            Unknown {@link ContentType}
	 */
	public UnknownContentTypeException(final ContentType contentType) {
		super("Unknown content type: " + (contentType != null ? contentType.name() : "null"));
		original = null;
	}

	/**
	 * Creates a new {@link UnknownContentTypeException}.
	 *
	 * @param contentType
	 *            Unknown {@link ContentType}
	 * @param original
	 *            Exception from an external library
	 */
	public UnknownContentTypeException(final ContentType contentType, final Exception original) {
		super("Unknown content type: " + (contentType != null ? contentType.name() : "null"));
		this.original = original;
	}

}
