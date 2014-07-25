package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by fdoyle on 7/22/14.
 */
public class ObservableOptionsNode implements IOptionsNode {

    IOptionsNode childNode;
    Type containerType;
    Type parameterType;


    public ObservableOptionsNode(Method m, Type myType, Type childType, int depth) {
        Class<?> childClass;
        if(childType instanceof Class) {
            childClass = (Class<?>) childType;
        } else {//if(childType instanceof ParameterizedType) {
            childClass = (Class<?>) ((ParameterizedType) childType).getRawType();
        }

        if(Collection.class.isAssignableFrom(childClass)) {
            childNode = new CollectionOptionsNode(m, childType, depth +1);
        } else {
            //assume that it contains plain objects
            childNode = new SingleObjectOptionsNode(m, (Class<?>) childType, depth + 1);//do this if this represents a collection of plain objects
        }
        this.parameterType = childType;
        this.containerType = myType;
    }

    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addObservable(containerType, parameterType);
        childNode.addToFlattenedOptions(flattenedOptions);
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        childNode.addDefaultSettingsTo(toAdd);
    }
}
