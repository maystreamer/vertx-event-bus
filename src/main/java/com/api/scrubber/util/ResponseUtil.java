package com.api.scrubber.util;

import static com.api.scrubber.util.JsonUtil.toJsonString;

import java.util.Date;

import com.api.scrubber.constant.Constants;
import com.api.scrubber.model.ScrubberRestResponse;

import io.vertx.reactivex.ext.web.RoutingContext;

public final class ResponseUtil {

	public static <T> void toResponse(T result, final RoutingContext routingContext, Date dt) {
		final ScrubberRestResponse<T> response;
		response = toApiResponse(200, Constants.RESPONSE_SUCCESS, (T) result, false);
		final String resp = toJsonString(response);
		routingContext.response().setStatusCode(response.getCode()).end(resp);
	}
	
	public static <T> ScrubberRestResponse<T> toApiResponse(final int code, final String message, final T data,
			final boolean hasError) {
		ScrubberRestResponse<T> response = new ScrubberRestResponse.Builder<T>().setCode(code).setHasError(hasError)
				.setData(data).setMessage(message).build();
		return response;
	}
	
	public static String getHeaderValue(final String headerName, final RoutingContext routingContext) {
		return routingContext.request().getHeader(headerName);
	}

}