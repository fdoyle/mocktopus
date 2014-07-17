package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

/**
 * Created by fdoyle on 7/16/14.
 */
public interface IOptionsNode {
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions);

    public void addDefaultSettingsTo(FieldSettings toAdd);
}
