package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.modder.IListModder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 7/24/14.
 * Sets a ListModder on an object, providing advanced configuration of objects in lists
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ListModder {
    Class<? extends IListModder> value();
}
