package de.jochor.lib.http4j;

/**
 * Exception thrown in adapter implementations when encountering a problem during a HTTP request.
 *
 * <p>
 * <b>Started:</b> 2015-11-22
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPRequestException extends RuntimeException {

	private static final long serialVersionUID = -7055866520126432653L;

	/**
	 * Creates a new {@link HTTPRequestException}.
	 *
	 * @param e
	 *            Parent exception
	 */
	public HTTPRequestException(Exception e) {
		super(e);
	}

}
