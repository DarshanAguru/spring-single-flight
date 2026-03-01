package com.darshan.springsingleflight.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleFlight {
    String key(); //spEL key
    long timeoutMs() default 3000;
}
