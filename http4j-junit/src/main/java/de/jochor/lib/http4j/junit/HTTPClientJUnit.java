package de.jochor.lib.http4j.junit;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import de.jochor.lib.http4j.HTTPClient;
import de.jochor.lib.http4j.model.BaseRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 * http4j implementation for jUnit. This is not an adapter and it will not make any real http requests. It is for unit
 * testing your application without setting up an http end point.
 *
 * <p>
 * <b>Started:</b> 2015-08-21
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPClientJUnit implements HTTPClient {

	protected static final HashMap<String, String> expectedHeaders = new HashMap<>();

	protected static final Queue<String> responses = new LinkedList<>();

	protected static final Queue<String[]> expectedParams = new LinkedList<>();

	/**
	 * Adds an expected header by name and value. Any request without those headers will receive a 401 response.
	 *
	 * @param name
	 *            Header name
	 * @param value
	 *            Header value
	 */
	public static void addExpectedHeader(String name, String value) {
		expectedHeaders.put(name, value);
	}

	/**
	 * Removes all expected headers.
	 */
	public static void clearExpectedHeaders() {
		expectedHeaders.clear();
	}

	/**
	 * Adds a predefined response to the response queue. On every request the next response is popped from the queue and
	 * returned. Optionally multiple parameters with values can be defined as to be expected in the request.
	 *
	 * @param response
	 *            Response to queue
	 * @param expectedParams
	 *            Parameters and values to be expected (optional)
	 */
	public static void addResponse(String response, String... expectedParams) {
		responses.add(response);
		HTTPClientJUnit.expectedParams.add(expectedParams);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(GetRequest request) {
		String response = executeRequest(request, null);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String post(PostRequest request) {
		String body = request.getBody();

		String response = executeRequest(request, body);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String put(PutRequest request) {
		String body = request.getBody();

		String response = executeRequest(request, body);

		return response;
	}

	protected String executeRequest(BaseRequest request, String body) {
		URI uri = request.getUri();
		HashMap<String, String> headers = request.getHeaders();
		int expectedStatus = request.getExpectedStatus();

		int responseStatus = checkHeaders(headers) ? 200 : 401;
		if (!checkParameters(uri, body)) {
			responseStatus = 401;
		}
		if (responseStatus != expectedStatus) {
			throw new IllegalStateException("Expected HTTP response status [" + expectedStatus + "] but instead got [" + responseStatus + "]");
		}

		if (responseStatus != 200) {
			return "";
		}

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

	private boolean checkHeaders(HashMap<String, String> headers) {
		for (Entry<String, String> expected : expectedHeaders.entrySet()) {
			String name = expected.getKey();
			String value = expected.getValue();

			String actualValue = headers.get(name);

			if (value != null && !value.equals(actualValue) || actualValue == null) {
				return false;
			}
		}
		return true;
	}

	private boolean checkParameters(URI uri, String body) {
		HashSet<String> parameters = new HashSet<>();

		String query = uri.getQuery();
		if (query != null) {
			String[] queryParts = query.split("&");
			parameters.addAll(Arrays.asList(queryParts));
		}

		if (body != null) {
			String json = body.trim();
			String jsonBody = json.substring(1, json.length() - 1);
			jsonBody = jsonBody.replaceAll("\"", "").replaceAll(":", "=");
			String[] jsonParts = jsonBody.split("\\s*,\\s*");
			parameters.addAll(Arrays.asList(jsonParts));
		}

		String[] expectedParams = HTTPClientJUnit.expectedParams.poll();

		boolean expectedParamsFound = parameters.containsAll(Arrays.asList(expectedParams));

		return expectedParamsFound;
	}

}
