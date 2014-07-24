package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.string;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fdoyle on 6/20/14.
 * used for strings that contain links to images
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface StringImageUrl {
    String value() default "http://www.willowtreeapps.com/wp-content/themes/wta-wordpress-theme/assets/images/logo@2x.png"; //todo change this
}

