package de.jochor.lib.http;

import de.jochor.lib.http.model.GetRequest;
import de.jochor.lib.http.model.PostRequest;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public interface HttpClient {

	String get(GetRequest request);

	String post(PostRequest request);

}
