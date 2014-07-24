package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.builder.IListBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 7/24/14.
 * allows a class to define how it should be created when in a collection
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ListBuilder {
    Class<? extends IListBuilder> value();
}
