package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Type;

/**
 * Created by fdoyle on 7/22/14.
 */
public class ObservableOptionsNode implements IOptionsNode {

    IOptionsNode childNode;
    Type containerType;
    Type parameterType;

    @Override
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {

    }

    @Override
    public void addDefaultSettingsTo(FieldSettings toAdd) {

    }
}
