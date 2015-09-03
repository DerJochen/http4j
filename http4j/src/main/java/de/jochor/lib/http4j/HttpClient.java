package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public interface HttpClient {

	String get(GetRequest request);

	String post(PostRequest request);

	String put(PutRequest request);

}
