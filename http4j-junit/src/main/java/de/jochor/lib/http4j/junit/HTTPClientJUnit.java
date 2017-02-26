package de.jochor.lib.http4j.junit;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.junit.Assert;

import de.jochor.lib.http4j.HTTPClient;
import de.jochor.lib.http4j.model.BaseRequest;
import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PatchRequest;
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

	protected static final Queue<String[]> expectedParamArrays = new LinkedList<>();

	protected static final Queue<RequestHolder> requestHolders = new LinkedList<>();

	/**
	 * Adds an expected header by name and value. Any request without those headers will receive a 401 response.
	 * <p>
	 * A fluent API would be a better solution to configure expectations and results, but for now this works.
	 * </p>
	 *
	 * @param name
	 *            Header name
	 * @param value
	 *            Header value
	 */
	public static void addExpectedHeader(final String name, final String value) {
		expectedHeaders.put(name, value);
	}

	/**
	 * Removes all expected headers.
	 */
	public static void clearExpectedHeaders() {
		expectedHeaders.clear();
	}

	// TODO How to get the RequestHolder for requests without an answer?

	/**
	 * Adds a predefined response to the response queue. On every request the next response is popped from the queue and
	 * returned. Optionally multiple parameters with values can be defined as to be expected in the request.
	 * <p>
	 * A fluent API would be a better solution to configure expectations and results, but for now this works.
	 * </p>
	 *
	 * @param response
	 *            Response to queue
	 * @param expectedParams
	 *            Parameters and values to be expected (optional)
	 * @return
	 */
	public static RequestHolder addResponse(final String response, final String... expectedParams) {
		responses.add(response);
		expectedParamArrays.add(expectedParams);
		RequestHolder requestHolder = new RequestHolder();
		requestHolders.add(requestHolder);

		return requestHolder;
	}

	/**
	 * Removes all remaining responses.
	 */
	public static void clearResponses() {
		responses.clear();
		expectedParamArrays.clear();
		requestHolders.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String delete(final DeleteRequest request) {
		int expectedStatus = request.getExpectedStatus();
		if (expectedStatus == 204) {
			return null;
		} else {
			String response = executeRequest(request, null);
			return response;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(final GetRequest request) {
		String response = executeRequest(request, null);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String patch(final PatchRequest request) {
		String body = request.getBody();

		String response = executeRequest(request, body);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String post(final PostRequest request) {
		String body = request.getBody();

		String response = executeRequest(request, body);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String put(final PutRequest request) {
		String body = request.getBody();

		String response = executeRequest(request, body);

		return response;
	}

	protected String executeRequest(final BaseRequest request, final String body) {
		URI uri = request.getUri();

		if (responses.isEmpty()) {
			String msg = "No more answers configured. Request was: '" + uri.toString() + "'";
			if (body != null) {
				msg += " - " + body;
			}
			throw new IllegalStateException(msg);
		}

		HashMap<String, String> headers = request.getHeaders();
		HashMap<String, String> queryParameters = request.getQueryParameters();
		int expectedStatus = request.getExpectedStatus();
		boolean expect200 = expectedStatus == 200;

		int responseStatus = 200;

		if (!checkHeaders(headers, expect200) || !checkParameters(uri, queryParameters, expect200)) {
			responseStatus = 404;
		}
		if (responseStatus != expectedStatus) {
			throw new IllegalStateException("Expected HTTP response status [" + expectedStatus + "] but instead got [" + responseStatus + "]");
		}

		if (responseStatus != 200) {
			return "";
		}

		String response = responses.poll();
		expectedParamArrays.poll();
		RequestHolder requestHolder = requestHolders.poll();
		requestHolder.setRequest(request);

		return response;
	}

	private static boolean checkHeaders(final HashMap<String, String> headers, final boolean expect200) {
		for (Entry<String, String> expected : expectedHeaders.entrySet()) {
			String name = expected.getKey();
			String value = expected.getValue();

			String actualValue = headers != null ? headers.get(name) : null;

			if (expect200) {
				Assert.assertEquals("Header '" + name + "'", value, actualValue);
			} else if (value != null && !value.equals(actualValue) || actualValue == null) {
				return false;
			}
		}

		return true;
	}

	private static boolean checkParameters(final URI uri, final HashMap<String, String> queryParameters, final boolean expect200) {
		String[] expectedParams = expectedParamArrays.peek();
		if (expectedParams.length == 0) {
			return true;
		}

		HashSet<String> parameters = new HashSet<>();
		readImpliciteParameters(uri, parameters, queryParameters);
		readExpliciteParameters(queryParameters, parameters);

		boolean expectedParamsFound = true;
		if (expect200) {
			for (String expectedParam : expectedParams) {
				Assert.assertTrue("Parameter " + expectedParam, parameters.contains(expectedParam));
			}
		} else {
			expectedParamsFound = parameters.containsAll(Arrays.asList(expectedParams));
		}

		return expectedParamsFound;
	}

	private static void readExpliciteParameters(final HashMap<String, String> queryParameters, final HashSet<String> parameters) {
		if (queryParameters == null) {
			return;
		}

		Iterator<Entry<String, String>> iter = queryParameters.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			parameters.add(entry.getKey() + "=" + entry.getValue());
		}
	}

	private static void readImpliciteParameters(final URI uri, final HashSet<String> parameters, final HashMap<String, String> queryParameters) {
		String query = uri.getQuery();
		if (query == null) {
			return;
		}

		String[] queryParts = query.split("&");
		for (String queryPart : queryParts) {
			int firstEquals = queryPart.indexOf('=');
			String paramName = queryPart.substring(0, firstEquals);
			if (queryParameters == null || !queryParameters.containsKey(paramName)) {
				parameters.add(queryPart);
			}
		}
	}
}
