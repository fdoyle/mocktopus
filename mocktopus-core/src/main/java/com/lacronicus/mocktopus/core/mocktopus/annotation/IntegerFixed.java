package com.lacronicus.mocktopus.core.mocktopus.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 6/20/14.
 * param: value to use as fixed value
 *
 * //todo change this to NumberFixed?
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IntegerFixed {
    String value();
}

