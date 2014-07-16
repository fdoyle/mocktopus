package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fdoyle on 7/10/14.
 * <p/>
 * some thoughts:
 * I could do the check for field types here, and return a different list based on what it needs
 * but then it'd have to return a list of Objects and not a list of Strings or Integers specifically
 * <p/>
 * maybe some generics wizardry?
 */
public class FieldOptionsListBuilder {

    public List<String> getOptionsForStringField(Field field) {
        //todo parse some annotations to see if we should do something else

        return Arrays.asList("hello", "hello\nhello\nhello\n", null);
    }


    //todo add parse methods for other types
}
