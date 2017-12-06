package com.api.scrubber.httpclient;

import java.net.MalformedURLException;
import java.net.URL;

import com.api.scrubber.model.APIRequest;

import io.vertx.core.json.JsonObject;

public abstract class AbstractHttpClient implements IHttpClient {
	protected final APIRequest apiRequest;
	protected final URL apiURL;

	public AbstractHttpClient(final APIRequest apiRequest) throws MalformedURLException {
		this.apiRequest = apiRequest;
		this.apiURL = new URL(apiRequest.getUrl());
	}

	protected String getUrl() {
		return apiURL.toString();
	}

	protected String getHost() {
		return apiURL.getHost();
	}

	protected int getPort() {
		if ("HTTPS".equalsIgnoreCase(getProtocol()))
			return 443;
		else if (apiURL.getPort() == -1)
			return apiURL.getDefaultPort();
		return apiURL.getPort();
	}

	protected String getProtocol() {
		return apiURL.getProtocol().toUpperCase();
	}

	protected String getPath() {
		return apiURL.getPath() + "?" + getQueryParam();
	}

	protected String getQueryParam() {
		return apiURL.getQuery();
	}

	protected String getPayload() {
		final JsonObject body = apiRequest.getBody();
		if (null != body && !body.isEmpty()) {
			return body.toString();
		}
		return "";
	}

	protected String getHeaderName(final String name) {
		return apiRequest.getHeaders().get(name);
	}
}