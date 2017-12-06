package com.api.scrubber.httpclient;

import java.net.MalformedURLException;
import java.util.Objects;

import com.api.scrubber.exception.RestException;
import com.api.scrubber.model.APIRequest;

public interface HttpClientFactory<P extends AbstractHttpClient> {
	static AbstractHttpClient create(final APIRequest apiRequest, final String clientType)
			throws MalformedURLException {
		Objects.requireNonNull(apiRequest);
		Objects.requireNonNull(clientType);
		switch (clientType) {
		case "squareok":
			return new SquareOkHttpClient(apiRequest);
		case "vertxrest":
			return new VertxHttpClient(apiRequest);
		default:
			throw new RestException("No implementation found for " + clientType);
		}
	}
}