package com.api.scrubber.httpclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.api.scrubber.exception.RestException;
import com.api.scrubber.helper.MediaType;
import com.api.scrubber.model.APIRequest;

import io.vertx.core.json.JsonObject;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SquareOkHttpClient extends AbstractHttpClient {
	private static final ConnectionPool pool = new ConnectionPool(50, 120, TimeUnit.SECONDS);
	private static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).connectionPool(pool).build();

	public SquareOkHttpClient(APIRequest apiRequest) throws MalformedURLException {
		super(apiRequest);
	}

	@Override
	public JsonObject doGet() {
		Request request = buildRequest().get().build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new RestException("There is an error while get: " + response);
			return buildJsonResponse(response);
		} catch (IOException ix) {
			throw new RestException("There is an io exception while get", ix);
		} catch (Exception ex) {
			throw new RestException("There is an exception while get", ex);
		}
	}

	@Override
	public JsonObject doPost() {
		RequestBody body = RequestBody.create(getMediaType(getHeaderName(MediaType.CONTENT_TYPE)), getPayload());
		Request request = buildRequest().post(body).build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new RestException("There is an error while post: " + response);
			return buildJsonResponse(response);
		} catch (IOException ix) {
			throw new RestException("There is an io exception while post", ix);
		} catch (Exception ex) {
			throw new RestException("There is an exception while post", ex);
		}
	}

	@Override
	public JsonObject doPut() {
		RequestBody body = RequestBody.create(getMediaType(getHeaderName(MediaType.CONTENT_TYPE)), getPayload());
		Request request = buildRequest().put(body).build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new RestException("There is an error while put: " + response);
			return buildJsonResponse(response);
		} catch (IOException ix) {
			throw new RestException("There is an io exception while put", ix);
		} catch (Exception ex) {
			throw new RestException("There is an exception while put", ex);
		}
	}

	@Override
	public JsonObject doDelete() {
		RequestBody body = null;
		Request request = null;
		final String payload = getPayload();
		if (!payload.isEmpty()) {
			body = RequestBody.create(getMediaType(getHeaderName(MediaType.CONTENT_TYPE)), getPayload());
			request = buildRequest().delete(body).build();
		} else {
			request = buildRequest().delete().build();
		}
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new RestException("There is an error while delete: " + response);
			return buildJsonResponse(response);
		} catch (IOException ix) {
			throw new RestException("There is an io exception while delete", ix);
		} catch (Exception ex) {
			throw new RestException("There is an exception while delete", ex);
		}
	}

	private okhttp3.MediaType getMediaType(final String mediaType) {
		return okhttp3.MediaType.parse(mediaType);
	}

	private Builder buildRequest() {
		Builder builder = new Request.Builder();
		builder.url(apiURL);
		getHeaders(builder);
		return builder;
	}

	private Builder getHeaders(Builder builder) {
		final Map<String, String> requestHeaders = apiRequest.getHeaders();
		if (null != requestHeaders && !requestHeaders.isEmpty()) {
			for (Entry<String, String> entry : requestHeaders.entrySet()) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		return builder;
	}

	private JsonObject buildJsonResponse(final Response response) throws IOException {
		final ResponseBody body = response.body();
		final String bodyStr = body.string();
		JsonObject json = null;
		if (null != bodyStr && !bodyStr.isEmpty()) {
			json = new JsonObject(bodyStr);
		} else {
			json = new JsonObject();
		}
		json.put("status", response.code());
		json.put("timeTakenMS", response.receivedResponseAtMillis() - response.sentRequestAtMillis());
		final long length = body.contentLength();
		if (length > 0)
			json.put("Content-Length", length);
		if (null != response.body().contentType()) {
			json.put("Content-Type", response.body().contentType().toString());
		}
		Headers responseHeaders = response.headers();
		Map<String, String> mapper = null;
		if (null != responseHeaders) {
			mapper = new HashMap<String, String>();
			for (int i = 0; i < responseHeaders.size(); i++) {
				mapper.put(responseHeaders.name(i), responseHeaders.value(i));
			}
		}
		json.put("response_headers", mapper);
		return json;
	}
}