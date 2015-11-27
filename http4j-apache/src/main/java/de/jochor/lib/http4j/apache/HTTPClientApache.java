package de.jochor.lib.http4j.apache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import de.jochor.lib.http4j.HTTPClient;
import de.jochor.lib.http4j.HTTPRequestException;
import de.jochor.lib.http4j.UnknownContentTypeException;
import de.jochor.lib.http4j.model.BaseContentRequest;
import de.jochor.lib.http4j.model.BaseRequest;
import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 * {@link HTTPClient} adapter for the Apache HTTP Components project.
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPClientApache implements HTTPClient {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String delete(DeleteRequest request) {
		HttpDelete httpRequest = new HttpDelete();

		String response = executeRequest(request, httpRequest);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(GetRequest request) {
		HttpGet httpRequest = new HttpGet();

		String response = executeRequest(request, httpRequest);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String post(PostRequest request) {
		String body = request.getBody();

		HttpPost httpRequest = new HttpPost();

		ContentType contentType = selectContentType(request);
		HttpEntity entity = new StringEntity(body, contentType);
		httpRequest.setEntity(entity);

		String response = executeRequest(request, httpRequest);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String put(PutRequest request) {
		String body = request.getBody();
		if (body == null) {
			body = "";
		}

		HttpPut httpRequest = new HttpPut();

		ContentType contentType = selectContentType(request);
		HttpEntity entity = new StringEntity(body, contentType);
		httpRequest.setEntity(entity);

		String response = executeRequest(request, httpRequest);

		return response;
	}

	protected String executeRequest(BaseRequest request, HttpRequestBase httpRequest) {
		try {
			URI uri = buildQueryString(request);
			int expectedStatus = request.getExpectedStatus();

			httpRequest.setURI(uri);
			fillHeaders(request, httpRequest);

			try (CloseableHttpClient client = HttpClientBuilder.create().build(); //
					CloseableHttpResponse httpResponse = client.execute(httpRequest)) {
				int responseStatus = httpResponse.getStatusLine().getStatusCode();
				if (responseStatus != expectedStatus) {
					throw new IllegalStateException("Expected HTTP response status [" + expectedStatus + "] " + "but instead got [" + responseStatus + "]");
				}

				if (!hasContent(responseStatus)) {
					return null;
				}

				HttpEntity entity = httpResponse.getEntity();

				String response = readResponse(entity);

				return response;
			}
		} catch (IOException | URISyntaxException e) {
			throw new HTTPRequestException(e);
		}
	}

	private static URI buildQueryString(BaseRequest request) throws URISyntaxException {
		URI uri = request.getUri();
		HashMap<String, String> queryParameters = request.getQueryParameters();

		if (queryParameters != null) {
			URIBuilder ub = new URIBuilder(uri);
			for (Entry<String, String> queryParameter : queryParameters.entrySet()) {
				String name = queryParameter.getKey();
				String value = queryParameter.getValue();

				ub.addParameter(name, value);
			}

			uri = ub.build();
		}

		return uri;
	}

	private static void fillHeaders(BaseRequest request, HttpRequestBase httpRequest) {
		HashMap<String, String> headers = request.getHeaders();
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> header : headers.entrySet()) {
				String name = header.getKey();
				String value = header.getValue();

				httpRequest.addHeader(name, value);
			}
		}
	}

	private static boolean hasContent(int responseStatus) {
		if (responseStatus == 204) {
			return false;
		}
		return true;
	}

	private static ContentType selectContentType(BaseContentRequest request) {
		de.jochor.lib.http4j.model.ContentType http4jContentType = request.getContentType();
		if (http4jContentType == de.jochor.lib.http4j.model.ContentType.APPLICATION_JSON) {
			return ContentType.APPLICATION_JSON;
		} else {
			throw new UnknownContentTypeException(http4jContentType);
		}
	}

	private static String readResponse(HttpEntity entity) throws IOException {
		Charset charset = selectCharset(entity);

		try (BufferedReader content = new BufferedReader(new InputStreamReader(entity.getContent(), charset))) {
			String line = content.readLine();
			if (line == null) {
				return "";
			}

			String nextLine = content.readLine();
			if (nextLine == null) {
				return line;
			}

			StringBuilder sb = new StringBuilder(line);
			do {
				sb.append('\n').append(nextLine);
				nextLine = content.readLine();
			} while (nextLine != null);

			String response = sb.toString();

			return response;
		}
	}

	private static Charset selectCharset(HttpEntity entity) {
		Charset charset;
		Header encodingHeader = entity.getContentEncoding();
		if (encodingHeader != null) {
			String charsetName = encodingHeader.getValue();
			charset = Charset.forName(charsetName);
		} else {
			charset = StandardCharsets.UTF_8;
		}

		return charset;
	}

}
