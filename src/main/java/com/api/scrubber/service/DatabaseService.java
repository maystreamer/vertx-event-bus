package com.api.scrubber.service;

import java.util.List;

import com.api.scrubber.model.APIRequest;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface DatabaseService {
	String ADDRESS = DatabaseService.class.getName();

	void saveRequest(final APIRequest aPIRequest, final Handler<AsyncResult<JsonObject>> resultHandler);

	void getRequest(final String id, final Handler<AsyncResult<JsonObject>> resultHandler);

	void getAllRequests(final String id, final Handler<AsyncResult<List<JsonObject>>> resultHandler);
}