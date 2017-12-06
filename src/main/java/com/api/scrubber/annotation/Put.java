package com.api.scrubber.annotation;


import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Put {
    String value();
    String method = HttpMethod.PUT.name();
}

