package com.api.scrubber.service;

import com.api.scrubber.config.ScrubberConfig;
import com.api.scrubber.constant.Constants;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

public enum MongoClientWrapper {
	INSTANCE;
	private MongoClient mongoClient;

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void createMongoClient(final Vertx vertx) {
		this.mongoClient = MongoClient.createShared(vertx, getMongoConfig());
	}

	private JsonObject getMongoConfig() {
		return ScrubberConfig.INSTANCE.getValueByEnvironment(Constants.MONGO_CONFIG);
	}
}