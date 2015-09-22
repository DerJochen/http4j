package de.jochor.lib.http4j.apache;

import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

import de.jochor.lib.http4j.HTTPClient;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 *
 * <p>
 * <b>Started:</b> 2015-08-21
 * </p>
 * 
 * @author Jochen Hormes
 *
 */
public class HTTPClientJUnit implements HTTPClient {

	protected static final Queue<String> responses = new LinkedList<>();

	public static void addResponse(String response) {
		responses.add(response);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(GetRequest request) {
		URI uri = request.getUri();

		String response = executeRequest(uri, null);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String post(PostRequest request) {
		URI uri = request.getUri();
		String body = request.getBody();

		String response = executeRequest(uri, body);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String put(PutRequest request) {
		URI uri = request.getUri();
		String body = request.getBody();

		String response = executeRequest(uri, body);

		return response;
	}

	protected String executeRequest(URI uri, String body) {
		String response = responses.poll();

		if (response != null) {
			return response;
		}

		String msg = "No more answers configured. Request was: '" + uri.toString();
		if (body != null) {
			msg += " - " + body;
		}
		throw new IllegalStateException(msg);
	}

}
