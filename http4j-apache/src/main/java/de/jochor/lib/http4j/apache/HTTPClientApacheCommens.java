package de.jochor.lib.http4j.apache;

import java.io.IOException;
import java.io.InputStream;
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
import de.jochor.lib.http4j.model.BaseRequest;
import de.jochor.lib.http4j.model.DeleteRequest;
import de.jochor.lib.http4j.model.GetRequest;
import de.jochor.lib.http4j.model.PostRequest;
import de.jochor.lib.http4j.model.PutRequest;

/**
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class HTTPClientApacheCommens implements HTTPClient {

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

		// TODO get content type from request
		HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
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

		// TODO get content type from request
		HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
		httpRequest.setEntity(entity);

		String response = executeRequest(request, httpRequest);

		return response;
	}

	protected String executeRequest(BaseRequest request, HttpRequestBase httpRequest) {
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
			try (InputStream content = entity.getContent()) {
				byte[] responseBody = new byte[(int) entity.getContentLength()];

				content.read(responseBody);

				Charset charset;
				Header encodingHeader = entity.getContentEncoding();
				if (encodingHeader != null) {
					String charsetName = encodingHeader.getValue();
					charset = Charset.forName(charsetName);
				} else {
					charset = StandardCharsets.UTF_8;
				}

				String response = new String(responseBody, charset);

				return response;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected URI buildQueryString(BaseRequest request) {
		URI uri = request.getUri();
		HashMap<String, String> queryParameters = request.getQueryParameters();

		if (queryParameters != null && !queryParameters.isEmpty()) {
			URIBuilder ub = new URIBuilder(uri);
			for (Entry<String, String> queryParameter : queryParameters.entrySet()) {
				String name = queryParameter.getKey();
				String value = queryParameter.getValue();

				ub.addParameter(name, value);
			}

			try {
				uri = ub.build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}

		return uri;
	}

	protected void fillHeaders(BaseRequest request, HttpRequestBase httpRequest) {
		HashMap<String, String> headers = request.getHeaders();
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> header : headers.entrySet()) {
				String name = header.getKey();
				String value = header.getValue();

				httpRequest.addHeader(name, value);
			}
		}
	}

	protected boolean hasContent(int responseStatus) {
		if (responseStatus == 204) {
			return false;
		}
		return true;
	}

}
