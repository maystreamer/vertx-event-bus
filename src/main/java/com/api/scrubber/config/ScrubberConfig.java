package com.api.scrubber.config;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.api.scrubber.constant.Constants;

import io.vertx.core.json.JsonObject;

public enum ScrubberConfig {
	INSTANCE;
	private JsonObject config;

	public JsonObject getConfig() {
		return config;
	}

	public void setConfig(final JsonObject config) {
		this.config = config;
	}

	public JsonObject loadEnvironment() {
		String environment = System.getenv(Constants.ENVIRONMENT);
		environment = !isBlank(environment) ? environment.trim().toLowerCase()
				: Constants.ENVIRONMENT_DEVELOPMENT.toLowerCase();
		return this.config.getJsonObject(environment);
	}

	public JsonObject getValueByEnvironment(final String key) {
		return loadEnvironment().getJsonObject(key);
	}
}