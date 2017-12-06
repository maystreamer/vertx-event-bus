/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.api.scrubber.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link com.api.scrubber.model.APIRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link com.api.scrubber.model.APIRequest} original class using Vert.x codegen.
 */
public class APIRequestConverter {

  public static void fromJson(JsonObject json, APIRequest obj) {
    if (json.getValue("body") instanceof JsonObject) {
      obj.setBody(((JsonObject)json.getValue("body")).copy());
    }
    if (json.getValue("headers") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("headers").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setHeaders(map);
    }
    if (json.getValue("httpMethod") instanceof String) {
      obj.setHttpMethod((String)json.getValue("httpMethod"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(APIRequest obj, JsonObject json) {
    if (obj.getBody() != null) {
      json.put("body", obj.getBody());
    }
    if (obj.getHeaders() != null) {
      JsonObject map = new JsonObject();
      obj.getHeaders().forEach((key,value) -> map.put(key, value));
      json.put("headers", map);
    }
    if (obj.getHttpMethod() != null) {
      json.put("httpMethod", obj.getHttpMethod());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}