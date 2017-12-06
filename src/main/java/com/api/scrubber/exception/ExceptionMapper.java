package com.api.scrubber.exception;

import com.api.scrubber.model.ScrubberRestError;

public class ExceptionMapper {

	public static ScrubberRestError build(Exception ex) {
		if (ex instanceof RestException) {
			RestException rx = (RestException) ex;
			final String message = rx.getCause() != null ? rx.getCause().getMessage() : rx.getMessage();
			return new ScrubberRestError.Builder().setCode(rx.getStatusCode()).setMessage(message).build();
		} else {
			return new ScrubberRestError.Builder().setCode(500).setMessage(ex.getMessage()).build();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T build(Throwable cause) {
		return (T) new ScrubberRestError.Builder().setCode(500).setMessage(cause.getMessage()).build();
	}
}