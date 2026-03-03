package com.darshan.springsingleflight.annotation;

import java.lang.annotation.*;

/**
 * Annotation to prevent cache stampede by ensuring a method is executed only
 * once
 * for concurrent requests with the same key. Subsequent concurrent requests
 * will
 * wait for the first execution to complete and share its result.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleFlight {

    /**
     * Spring Expression Language (SpEL) expression to resolve the unique key
     * for the single flight execution boundary. E.g. "'user:' + #id"
     * 
     * @return the SpEL expression for the cache key
     */
    String key();

    /**
     * The maximum time in milliseconds to wait for the ongoing executing request to
     * complete.
     * If the timeout is exceeded, a timeout exception is thrown.
     * 
     * @return timeout in milliseconds
     */
    long timeoutMs() default 3000;
}
