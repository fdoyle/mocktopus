package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by fdoyle on 7/16/14.
 * represents a flat list of options (for UI purposes) rather than the tree structure Options usually takes
 * any good reason it doesn't always exist as a list? only needs to exist as a tree while parsing the
 * interface, after that it's only when configuring (which uses this) and when actually using the api
 * (which can probably use this as well)
 */
public class FlattenedOptions {


    public class FlatOptionsItem {
        public MethodItem methodItem;
        public FieldItem fieldItem;

    }

    public class MethodItem {
        public Method method;
        public String endpoint;

    }

    public class FieldItem {
        public Field field;
        public List<Object> fieldOptions;
        public Object currentSetting;
    }
}
