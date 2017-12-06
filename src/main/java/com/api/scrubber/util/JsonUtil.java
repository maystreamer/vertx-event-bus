package com.api.scrubber.util;

import java.util.Map;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class JsonUtil {

	public static <T> String toJsonString(T object) {
		return Json.encode(object);
	}

	@SuppressWarnings("unchecked")
	public static <T> JsonObject toJsonObject(T object) {
		return new JsonObject((Map<String, Object>) Json.mapper.convertValue(object, Map.class));
	}

	public static <T> T toTargetObject(JsonObject jsonObject, Class<T> clazz) {
		return Json.decodeValue(jsonObject.toString(), clazz);
	}
}
