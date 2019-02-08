package com.walterjwhite.property.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a value as the default if one is not explicitly set via command-line, environment, or
 * properties file.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
public @interface DefaultValue {}
