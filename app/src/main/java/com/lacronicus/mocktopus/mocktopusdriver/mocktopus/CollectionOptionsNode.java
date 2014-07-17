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
    Type containerType;
    Class containedClass;

    public CollectionOptionsNode(Method m, Type ContainerType, Class containedClass, int depth) {
        node = new SingleObjectOptionsNode(m, containedClass, depth + 1);
        this.containedClass = containedClass;
        this.containerType = ContainerType;
    }


    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addCollection(containerType, containedClass);
        node.addToFlattenedOptions(flattenedOptions);
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        node.addDefaultSettingsTo(toAdd);
    }

}
