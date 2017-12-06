package com.api.scrubber.httpclient;

import java.net.MalformedURLException;

import com.api.scrubber.model.APIRequest;

import io.vertx.core.json.JsonObject;

public class VertxHttpClient extends AbstractHttpClient {

	public VertxHttpClient(APIRequest apiRequest) throws MalformedURLException {
		super(apiRequest);
	}

	@Override
	public JsonObject doGet() {
		return null;
	}

	@Override
	public JsonObject doPost() {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public JsonObject doPut() {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public JsonObject doDelete() {
		return null;
		// TODO Auto-generated method stub

	}
}