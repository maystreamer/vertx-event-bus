package com.api.scrubber.handler;

import static com.api.scrubber.constant.Constants.REQUEST_HEADER_NAME;
import static com.api.scrubber.constant.Constants.RESPONSE_FAILED;
import static com.api.scrubber.util.ResponseUtil.toResponse;

import java.util.Date;

import com.api.scrubber.annotation.Post;
import com.api.scrubber.annotation.Service;
import com.api.scrubber.event.Event;
import com.api.scrubber.exception.RestException;
import com.api.scrubber.model.APIRequest;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * This is part of HTTPServerVerticle which will receive requests from outside
 * 
 * @author skukr6
 *
 */
@Service(uri = "/request")
public class RequestHandler extends BaseHandler {
	private static Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	public RequestHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	@Post(value = "/do")
	public void handle(RoutingContext event) {
		JsonObject jsonRequest = null;
		final String header = getRequestHeader(event, REQUEST_HEADER_NAME);
		try {
			jsonRequest = event.getBodyAsJson();
			final APIRequest apiRequest = new APIRequest(jsonRequest);
			final JsonObject json = apiRequest.toJson();
			// TODO: validations can be set here before sending to request processor
			DeliveryOptions options = new DeliveryOptions().addHeader("X-CORRELATION-ID", header);
			this.vertx.eventBus().rxSend(Event.REQUEST_EXECUTE, json, options).subscribe(res -> {
				logger.info("Successfully processed request having cor-relation id : " + header + " : [{}]", json);
				toResponse(res.body(), event, new Date());
			}, ex -> {
				logger.error("Error while executing request having cor-relation id : " + header + " : [{}] : ", json,
						ex);
				event.fail(ex);
			});
		} catch (Exception ex) {
			logger.error("Error while executing request having cor-relation id : " + header + " : [{}] : ", jsonRequest,
					ex);
			event.fail(new RestException(RESPONSE_FAILED, ex, 500));
		}
	}

}