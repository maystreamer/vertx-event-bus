package com.api.scrubber.service;

import java.util.List;

import com.api.scrubber.constant.Constants;
import com.api.scrubber.exception.RestException;
import com.api.scrubber.model.APIRequest;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.WriteOption;
import io.vertx.reactivex.ext.mongo.MongoClient;

public class DatabaseServiceImpl implements DatabaseService {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);
	private final MongoClient dbClient = MongoClientWrapper.INSTANCE.getMongoClient();

	@Override
	public void saveRequest(APIRequest aPIRequest, Handler<AsyncResult<JsonObject>> resultHandler) {
		Single<String> response = this.dbClient.rxSaveWithOptions(Constants.MONGO_COLLECTION_NAME, aPIRequest.toJson(),
				WriteOption.ACKNOWLEDGED);
		response.doOnError(cause -> {
			throw new RestException("Error while saving request", cause, 500);
		}).subscribe(result -> {
			JsonObject json = new JsonObject();
			json.put("hasError", false);
			json.put("message", result);
			logger.info("subscribed");
			Future.succeededFuture(json).setHandler(resultHandler);
		});
	}

	@Override
	public void getRequest(String id, Handler<AsyncResult<JsonObject>> resultHandler) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAllRequests(String id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
		// TODO Auto-generated method stub

	}
}