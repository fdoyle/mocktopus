package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 7/10/14.
 * used on strings that are filled with dates
 * <p/>
 * ex:
 *
 * @StringDate
 * @StringDate("yyyy")
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface StringDate {
    String value() default "yyyy-MM-dd'T'HH:mm'Z'"; //use iso format by default
}

