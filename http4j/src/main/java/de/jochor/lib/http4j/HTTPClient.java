package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 * TODO descr.
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
	 * TODO JavaDoc
	 *
	 * @param request
	 *            TODO descr.
	 * @return TODO descr.
	 */
	String delete(DeleteRequest request);

	/**
	 * TODO JavaDoc
	 *
	 * @param request
	 *            TODO descr.
	 * @return TODO descr.
	 */
	String get(GetRequest request);

	/**
	 * TODO JavaDoc
	 *
	 * @param request
	 *            TODO descr.
	 * @return TODO descr.
	 */
	String post(PostRequest request);

	/**
	 * TODO JavaDoc
	 *
	 * @param request
	 *            TODO descr.
	 * @return TODO descr.
	 */
	String put(PutRequest request);

}
