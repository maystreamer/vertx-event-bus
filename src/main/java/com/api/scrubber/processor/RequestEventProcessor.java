package com.api.scrubber.processor;

import java.net.MalformedURLException;

import com.api.scrubber.exception.RestException;
import com.api.scrubber.httpclient.HttpClientFactory;
import com.api.scrubber.httpclient.IHttpClient;
import com.api.scrubber.model.APIRequest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.MessageConsumer;

public class RequestEventProcessor extends AbstractEventProcessor {

	public RequestEventProcessor(final MessageConsumer<JsonObject> consumer) {
		super(consumer);
	}

	public void execute() {
		consumer.handler(m -> {
			try {
				APIRequest request = new APIRequest(m.body());
				final JsonObject response = doRequest(request);
				m.reply(response);
			} catch (EncodeException ex) {
				m.fail(HttpResponseStatus.BAD_REQUEST.code(), "Failed to encode data.");
			} catch (Exception ex) {
				m.fail(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
			}
		}).exceptionHandler(ex -> {
			throw new RestException("Error while executing request", ex, 500);
		});
	}

	private JsonObject doRequest(final APIRequest apiRequest) throws MalformedURLException {
		IHttpClient client = HttpClientFactory.create(apiRequest, "squareok");
		switch (getHttpMethod(apiRequest)) {
		case GET:
			return client.doGet();
		case POST:
			return client.doPost();
		case DELETE:
			return client.doDelete();
		case PUT:
			return client.doPut();
		default:
			throw new RestException("Method not implemented");
		}
	}

	private static HttpMethod getHttpMethod(final APIRequest apiRequest) {
		return HttpMethod.valueOf(apiRequest.getHttpMethod().toUpperCase());
	}
}
