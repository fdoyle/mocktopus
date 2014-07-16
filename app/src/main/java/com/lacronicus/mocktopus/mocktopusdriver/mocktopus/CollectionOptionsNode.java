package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fdoyle on 7/16/14.
 */
public class CollectionOptionsNode {

    OptionsNode node; // single options node representing all the contents of the associated collection
    Class clazz;
    Class genericClass;

    public CollectionOptionsNode(Method m, Class clazz, Class genericClass, int depth) {
        node = new OptionsNode(m, genericClass, depth + 1);
        this.genericClass = genericClass;
        this.clazz = clazz;
    }


    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addCollection(clazz, genericClass);
        node.addToFlattenedOptions(flattenedOptions);
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        node.addDefaultSettingsTo(toAdd);
    }

}
