package com.api.scrubber.processor;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.MessageConsumer;

public abstract class AbstractEventProcessor implements IRequestProcessor {
	protected MessageConsumer<JsonObject> consumer;

	public AbstractEventProcessor(final MessageConsumer<JsonObject> consumer) {
		this.consumer = consumer;
	}
}