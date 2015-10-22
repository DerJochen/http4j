package de.jochor.lib.http4j;

import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 * <p>
 * <b>Started:</b> 2015-10-03
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class StaticHTTPClientBinder {

	public static HTTPClient create() {
		return new HTTPClient() {

			@Override
			public String delete(DeleteRequest deleteRequest) {
				return null;
			}

			@Override
			public String get(GetRequest request) {
				return null;
			}

			@Override
			public String post(PostRequest request) {
				return null;
			}

			@Override
			public String put(PutRequest request) {
				return null;
			}

		};
	}

}
