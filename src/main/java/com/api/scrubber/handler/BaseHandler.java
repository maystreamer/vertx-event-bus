package com.api.scrubber.handler;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

public abstract class BaseHandler implements Handler<RoutingContext> {
	protected Vertx vertx;

	public BaseHandler(final Vertx vertx) {
		this.vertx = vertx;
	}

	protected String getRequestHeader(final RoutingContext context, final String headerName) {
		return context.request().getHeader(headerName);
	}

}