package com.lacronicus.mocktopus.mocktopusdriver.dagger;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by fdoyle on 7/17/14.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForActivity {

}
