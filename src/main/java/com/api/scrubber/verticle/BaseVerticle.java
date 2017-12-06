package com.api.scrubber.verticle;

import com.api.scrubber.config.ScrubberConfig;
import com.api.scrubber.constant.Constants;

import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;

public class BaseVerticle extends AbstractVerticle {
	public static String CONTEXT_PATH = ScrubberConfig.INSTANCE.getConfig().getString(Constants.CONTEXT_PATH);

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
	}
}