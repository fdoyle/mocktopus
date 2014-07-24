package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 7/24/14.
 *
 * tells mocktopus to ignore a field
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DontMock {

}

