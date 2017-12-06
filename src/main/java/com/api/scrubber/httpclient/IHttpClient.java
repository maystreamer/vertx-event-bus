package com.api.scrubber.httpclient;

import io.vertx.core.json.JsonObject;

public interface IHttpClient {
	public JsonObject doGet();

	public JsonObject doPost();

	public JsonObject doPut();

	public JsonObject doDelete();
}
