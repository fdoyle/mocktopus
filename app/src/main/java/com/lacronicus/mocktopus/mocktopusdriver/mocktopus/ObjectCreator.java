package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection.ListBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.builder.IListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by fdoyle on 7/24/14.
 */
public class ObjectCreator {

    /**
     * this is called recursively to "inflate" the object
     * this doesn't need to be in Options, could (should?) be taken out.
     *
     * this needs reworking too
     *
     * some notes:
     * each created object or primitive is either the base object to be returned, or
     * will be the contents of a field
     *
     * if this
     *
     *
     * */
    public static Object createObject(Type returnType, Method method, FieldSettings currentSettings) {
        log("creating a new object");
        Class<?> returnClass;
        if (returnType instanceof Class) {
            returnClass = (Class<?>) returnType;
        } else {//if(childType instanceof ParameterizedType) {
            returnClass = (Class<?>) ((ParameterizedType) returnType).getRawType();
        } //is there ever going to be something else?
        try {
            //handle observable
            if (Observable.class.isAssignableFrom(returnClass)) {
                Type containedClass = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                return Observable.from(createObject(containedClass, method, currentSettings));
            } else if (Collection.class.isAssignableFrom(returnClass)) {
                log("returnClass is collection " + returnClass.getSimpleName());
                List<Object> collection = new ArrayList<Object>();
                Type containedType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                log("adding three items to collection");
                if(containedType instanceof Class<?> && ((Class<?>) containedType).isAnnotationPresent(ListBuilder.class)) {
                    Class containedClass = (Class<?>) containedType;
                    ListBuilder builderAnnotation = (ListBuilder) containedClass.getAnnotation(ListBuilder.class);
                    IListBuilder builder = builderAnnotation.value().newInstance();
                    int count = builder.getCount();
                    for(int i = 0; i != count; i++) {
                        collection.add(createObject(containedType, method, currentSettings));
                    }
                    builder.modifyList(collection);
                } else {

                    //todo what happens when this List is supposed to contain a string?
                    //createObject can't handle a state where it needs to return a String
                    collection.add(createObject(containedType, method, currentSettings));
                    collection.add(createObject(containedType, method, currentSettings));
                    collection.add(createObject(containedType, method, currentSettings));
                }

                return collection;
            } else {
                Object response = returnClass.newInstance();
                Field[] fields = returnClass.getDeclaredFields(); // todo add support for super classes here

                for (int i = 0; i != fields.length; i++) {
                    //should the contents of this loop be replaced with a call to createObject?
                    //see todo right before



                    Field field = fields[i];
                    Class fieldType = field.getType();
                    if (fieldType.equals(String.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Integer.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(int.class)) {
                        field.setInt(response, (Integer) currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Long.class)) {
                        //todo
                    } else if (fieldType.equals(Double.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(double.class)) {
                        field.setDouble(response, (Double) currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Float.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(float.class)) {
                        field.setFloat(response, (Float) currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Character.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(char.class)) {
                        field.setChar(response, (Character) currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Short.class)) {
                        //todo
                    } else if (fieldType.equals(Byte.class)) {
                        //todo
                    } else if (fieldType.equals(Boolean.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(boolean.class)) {
                        field.setBoolean(response, (Boolean) currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                        // may need to explicity state what children to add
                        // what does Gson do? derp, it knows because the json already has structure, not because of any special knowledge
                        // about the fields. hmm...

                        //log("loading new object into " + field.getName());
                        field.set(response, createObject(field.getGenericType(), method, currentSettings));

                    }
                }
                return response;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static void log(String statement) {
        Log.d(Tag.mainTag + Options.class.getName(), statement);
    }
}
