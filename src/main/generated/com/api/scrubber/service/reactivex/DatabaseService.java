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

package com.api.scrubber.service.reactivex;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import java.util.List;
import com.api.scrubber.model.APIRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


@io.vertx.lang.reactivex.RxGen(com.api.scrubber.service.DatabaseService.class)
public class DatabaseService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DatabaseService that = (DatabaseService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.reactivex.TypeArg<DatabaseService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
    obj -> new DatabaseService((com.api.scrubber.service.DatabaseService) obj),
    DatabaseService::getDelegate
  );

  private final com.api.scrubber.service.DatabaseService delegate;
  
  public DatabaseService(com.api.scrubber.service.DatabaseService delegate) {
    this.delegate = delegate;
  }

  public com.api.scrubber.service.DatabaseService getDelegate() {
    return delegate;
  }

  public void saveRequest(APIRequest aPIRequest, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.saveRequest(aPIRequest, resultHandler);
  }

  public Single<JsonObject> rxSaveRequest(APIRequest aPIRequest) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
      saveRequest(aPIRequest, handler);
    });
  }

  public void getRequest(String id, Handler<AsyncResult<JsonObject>> resultHandler) { 
    delegate.getRequest(id, resultHandler);
  }

  public Single<JsonObject> rxGetRequest(String id) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
      getRequest(id, handler);
    });
  }

  public void getAllRequests(String id, Handler<AsyncResult<List<JsonObject>>> resultHandler) { 
    delegate.getAllRequests(id, resultHandler);
  }

  public Single<List<JsonObject>> rxGetAllRequests(String id) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<List<JsonObject>>(handler -> {
      getAllRequests(id, handler);
    });
  }


  public static  DatabaseService newInstance(com.api.scrubber.service.DatabaseService arg) {
    return arg != null ? new DatabaseService(arg) : null;
  }
}
