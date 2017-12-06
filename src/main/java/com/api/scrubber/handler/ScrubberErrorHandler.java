package com.api.scrubber.handler;

import static com.api.scrubber.constant.Constants.RESPONSE_FAILED;
import static com.api.scrubber.util.JsonUtil.toJsonString;
import static com.api.scrubber.util.ResponseUtil.toApiResponse;

import com.api.scrubber.exception.ExceptionMapper;
import com.api.scrubber.exception.RestException;
import com.api.scrubber.model.ScrubberRestError;
import com.api.scrubber.model.ScrubberRestResponse;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

public class ScrubberErrorHandler extends BaseHandler {

	public ScrubberErrorHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext routingContext) {
		final ScrubberRestResponse<ScrubberRestError> response;
		final RestException exception = getRestException(routingContext.failure());
		response = toApiResponse(exception.getStatusCode(), RESPONSE_FAILED, ExceptionMapper.build(exception), true);
		routingContext.response().setStatusCode(exception.getStatusCode());
		routingContext.response().end(toJsonString(response));
	}

	private RestException getRestException(Throwable t) {
		if (t instanceof RestException) {
			return (RestException) t;
		} else {
			return new RestException(t.getMessage(), t, 500);
		}
	}
}