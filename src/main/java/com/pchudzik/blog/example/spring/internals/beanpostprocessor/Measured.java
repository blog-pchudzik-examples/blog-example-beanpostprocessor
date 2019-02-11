package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Measured {
    String DEFAULT_PERFORMANCE_LOGGER = "PERFORMANCE";

    String value() default DEFAULT_PERFORMANCE_LOGGER;
}
