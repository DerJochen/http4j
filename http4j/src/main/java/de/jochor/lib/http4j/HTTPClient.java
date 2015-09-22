package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 * @author Jochen Hormes
 *
 */
public interface HTTPClient {

	String get(GetRequest request);

	String post(PostRequest request);

	String put(PutRequest request);

}
