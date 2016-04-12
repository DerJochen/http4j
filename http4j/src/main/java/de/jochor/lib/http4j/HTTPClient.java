package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PatchRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 * Interface of the http4j HTTP client. http4j is used by implementing against this interface.
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public interface HTTPClient {

	/**
	 * Sends a HTTP DELETE request.
	 *
	 * @param request
	 *            Request to send
	 * @return Returned content, if any
	 */
	String delete(DeleteRequest request);

	/**
	 * Sends a HTTP GET request.
	 *
	 * @param request
	 *            Request to send
	 * @return Returned content, if any
	 */
	String get(GetRequest request);

	/**
	 * Sends a HTTP PATCH request.
	 *
	 * @param request
	 *            Request to send
	 * @return Returned content, if any
	 */
	String patch(PatchRequest request);

	/**
	 * Sends a HTTP POST request.
	 *
	 * @param request
	 *            Request to send
	 * @return Returned content, if any
	 */
	String post(PostRequest request);

	/**
	 * Sends a HTTP PUT request.
	 *
	 * @param request
	 *            Request to send
	 * @return Returned content, if any
	 */
	String put(PutRequest request);

}
