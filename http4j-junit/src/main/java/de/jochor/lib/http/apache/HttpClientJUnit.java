package de.jochor.lib.http.apache;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

import de.jochor.lib.http.HttpClient;
import de.jochor.lib.http.HttpClientBuilder;
import de.jochor.lib.http.model.GetRequest;
import de.jochor.lib.http.model.PostRequest;
import de.jochor.lib.http.model.PutRequest;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-21
 *
 */
public class HttpClientJUnit implements HttpClient {

	protected static final Queue<String> responses = new LinkedList<>();

	static {
		try {
			Field classField = HttpClientBuilder.class.getDeclaredField("httpClientClass");
			classField.setAccessible(true);
			classField.set(null, HttpClientJUnit.class);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

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
