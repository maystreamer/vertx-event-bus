package com.api.scrubber.handler;

import static com.api.scrubber.constant.Constants.RESPONSE_FAILED;
import static com.api.scrubber.constant.Constants.RESPONSE_SUCCESS;
import static com.api.scrubber.helper.MediaType.APPLICATION_JSON_VALUE;
import static com.api.scrubber.helper.MediaType.CONTENT_TYPE;
import static com.api.scrubber.util.JsonUtil.toJsonString;
import static com.api.scrubber.util.ResponseUtil.toApiResponse;

import com.api.scrubber.annotation.Get;
import com.api.scrubber.annotation.Service;
import com.api.scrubber.exception.RestException;
import com.api.scrubber.model.ScrubberRestResponse;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

@Service(uri = "/ping")
public class PingHandler extends BaseHandler {

	public PingHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	@Get(value = "/do")
	public void handle(RoutingContext ctx) {
		ScrubberRestResponse<JsonObject> response;
		JsonObject result;
		try {
			result = new JsonObject().put("status", "OK");
			response = toApiResponse(200, RESPONSE_SUCCESS, result, false);
			ctx.response().putHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE).end(toJsonString(response));
		} catch (Exception ex) {
			ctx.fail(new RestException(RESPONSE_FAILED, ex, 500));
		}
	}
}