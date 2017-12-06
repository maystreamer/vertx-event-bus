package com.api.scrubber.model;

import java.util.Map;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class APIRequest {
	private String id;
	private String url;
	private String httpMethod;
	private JsonObject body;
	private Map<String, String> headers;

	// Mandatory for data objects
	public APIRequest(JsonObject jsonObject) {
		APIRequestConverter.fromJson(jsonObject, this);
	}

	public APIRequest() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public JsonObject getBody() {
		return body;
	}

	public void setBody(JsonObject body) {
		this.body = body;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		APIRequestConverter.toJson(this, json);
		return json;
	}

}