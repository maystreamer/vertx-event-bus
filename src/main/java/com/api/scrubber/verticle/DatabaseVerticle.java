package com.api.scrubber.verticle;

import com.api.scrubber.service.DatabaseService;
import com.api.scrubber.service.MongoClientWrapper;

import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class DatabaseVerticle extends BaseVerticle {
	private DatabaseService databaseService;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			MongoClientWrapper.INSTANCE.createMongoClient(vertx);
			databaseService = new ServiceProxyBuilder(vertx.getDelegate()).setAddress(DatabaseService.ADDRESS)
					.build(DatabaseService.class);
			startFuture.complete();
		} catch (Exception ex) {
			ex.printStackTrace();
			startFuture.fail(ex);
		}
	}
}
