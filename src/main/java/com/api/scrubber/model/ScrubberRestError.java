package com.api.scrubber.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * @author saurabh Generic error object to be thrown for any occurence of error
 *         while accessing the APIs.
 */

@JsonDeserialize(builder = ScrubberRestError.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrubberRestError {
	// Status code - HTTP or any custom
	private final Integer code;
	// Error message to be thrown
	private final String message;

	private ScrubberRestError(Builder builder) {
		this.code = builder.code;
		this.message = builder.message;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {
		private Integer code;
		private String message;

		public Builder() {
		}

		public Builder setCode(Integer code) {
			this.code = code;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public ScrubberRestError build() {
			return new ScrubberRestError(this);
		}
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}