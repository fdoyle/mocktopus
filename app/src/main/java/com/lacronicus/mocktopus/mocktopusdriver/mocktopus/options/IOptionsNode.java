package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.options;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.FlattenedOptions;

/**
 * Created by fdoyle on 7/16/14.
 */
public interface IOptionsNode {
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions);

    public void addDefaultSettingsTo(FieldSettings toAdd);
}
