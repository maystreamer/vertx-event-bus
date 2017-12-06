package com.api.scrubber.verticle;

import com.api.scrubber.event.Event;
import com.api.scrubber.processor.RequestEventProcessor;

import io.vertx.core.Future;

//https://github.com/vert-x3/vertx-examples/tree/master/core-examples/src/main/java/io/vertx/example/core/eventbus/messagecodec
public class RequestVerticle extends BaseVerticle {

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			RequestEventProcessor processor = new RequestEventProcessor(
					vertx.eventBus().consumer(Event.REQUEST_EXECUTE));
			processor.execute();
			startFuture.complete();
		} catch (Exception ex) {
			ex.printStackTrace();
			startFuture.fail(ex);
		}
	}
}
