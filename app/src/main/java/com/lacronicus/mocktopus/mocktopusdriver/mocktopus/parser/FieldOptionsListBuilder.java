package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringDate;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringFixed;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringImageUrl;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringWebpageUrl;

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
            StringImageUrl fixed = field.getAnnotation(StringImageUrl.class);
            returnList.add(fixed.value());
        }

        if(field.isAnnotationPresent(StringWebpageUrl.class)) {
            StringWebpageUrl fixed = field.getAnnotation(StringWebpageUrl.class);
            returnList.add(fixed.value());
        }

        returnList.add("Simple String");
        returnList.add("multi\nline\nstring");
        returnList.add("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        returnList.add(null);


        return returnList;
    }

    public List<Object> getOptionsforIntegerField(Field field) {
        List<Object> returnList = new ArrayList<Object>();
        returnList.add(1);
        returnList.add(-1);
        returnList.add(0);
        returnList.add(null);
        return returnList;
    }

    public List<Object> getOptionsforDoubleField(Field field) {
        List<Object> returnList = new ArrayList<Object>();
        returnList.add(1.0);
        returnList.add(-1.0);
        returnList.add(0);
        returnList.add(null);
        return returnList;
    }

    public List<Object> getOptionsforFloatField(Field field) {
        List<Object> returnList = new ArrayList<Object>();
        returnList.add(1f);
        returnList.add(-1f);
        returnList.add(0f);
        returnList.add(null);
        return returnList;
    }

    public List<Object> getOptionsforCharField(Field field) {
        List<Object> returnList = new ArrayList<Object>();
        returnList.add('a');
        returnList.add('\n');
        returnList.add(null);
        return returnList;
    }

    public List<Object> getOptionsforBooleanField(Field field) {
        List<Object> returnList = new ArrayList<Object>();
        returnList.add(true);
        returnList.add(false);
        returnList.add(null);
        return returnList;
    }


    //todo other types?
}
