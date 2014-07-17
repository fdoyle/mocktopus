package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by fdoyle on 7/16/14.
 * //todo combine this with OptionsNode
 * //because what if you have an object that extends List<Whatever> that also does other stuff?
 */
public class CollectionOptionsNode implements IOptionsNode{

    SingleObjectOptionsNode node; // single options node representing all the contents of the associated collection
    Type clazz;
    Class genericClass;

    public CollectionOptionsNode(Method m, Type clazz, Class genericClass, int depth) {
        node = new SingleObjectOptionsNode(m, genericClass, depth + 1);
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
