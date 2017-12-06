package com.api.scrubber.annotation;


import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {
    String value();
    String method = HttpMethod.DELETE.name();
}

