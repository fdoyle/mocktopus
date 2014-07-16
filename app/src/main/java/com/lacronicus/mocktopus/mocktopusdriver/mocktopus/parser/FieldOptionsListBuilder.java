package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringDate;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringFixed;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringImageUrl;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public List<Object> getOptionsForStringField(Field field) {
        List<Object> returnList = new ArrayList<Object>();

        //todo sane ordering of annotations? which ones are most important and should be default


        if(field.isAnnotationPresent(StringFixed.class)) {
            StringFixed fixed = field.getAnnotation(StringFixed.class);
            returnList.add(fixed.value());
        }
        if(field.isAnnotationPresent(StringDate.class)) {
            String formatString = field.getAnnotation(StringDate.class).value();
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            String dateString = format.format(new Date());
            returnList.add(dateString);


        }
        if(field.isAnnotationPresent(StringImageUrl.class)) {
            //todo
        }

        returnList.add("hello");
        returnList.add("hello\nhello\nhello\n");
        returnList.add(null);


        return returnList;
    }


    //todo add parse methods for other types
}
