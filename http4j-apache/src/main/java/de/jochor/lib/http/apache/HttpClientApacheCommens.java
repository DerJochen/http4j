package de.jochor.lib.http.apache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import de.jochor.lib.http.HttpClient;
import de.jochor.lib.http.model.GetRequest;
import de.jochor.lib.http.model.PostRequest;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
public class HttpClientApacheCommens implements HttpClient {

	@Override
	public String get(GetRequest request) {
		URI uri = request.getUri();
		int expectedStatus = request.getExpectedStatus();

		HttpGet httpRequest = new HttpGet(uri);

		String response = executeRequest(httpRequest, expectedStatus);

		return response;
	}

	@Override
	public String post(PostRequest request) {
		URI uri = request.getUri();
		int expectedStatus = request.getExpectedStatus();
		String body = request.getBody();

		HttpPost httpRequest = new HttpPost(uri);

		// TODO get content type from request
		HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
		httpRequest.setEntity(entity);

		String response = executeRequest(httpRequest, expectedStatus);

		return response;
	}

	protected String executeRequest(HttpUriRequest httpRequest, int expectedStatus) {
		try (CloseableHttpClient client = HttpClientBuilder.create().build(); //
				CloseableHttpResponse httpResponse = client.execute(httpRequest)) {
			int responseStatus = httpResponse.getStatusLine().getStatusCode();
			if (responseStatus != expectedStatus) {
				throw new IllegalStateException("Expected HTTP response status " + expectedStatus + " " + "but instead got [" + responseStatus + "]");
			}

			HttpEntity entity = httpResponse.getEntity();
			try (InputStream content = entity.getContent()) {
				byte[] responseBody = new byte[(int) entity.getContentLength()];

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

}
