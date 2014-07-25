package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection.ListModder;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.modder.IListModder;

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
     * <p/>
     * this could use some love
     *
     * @param returnType the type of the object being created
     * @param method the method this object is being created as a return value for
     * @param f the field the object is about to be put into, null if it's the top-level object
     * @param currentSettings the settings object being used to create new methods
     */
    public static Object createObject(Type returnType, Method method, Field f, FieldSettings currentSettings) {
        log("creating a new object");
        Class<?> returnClass;
        if (returnType instanceof Class) {
            returnClass = (Class<?>) returnType;
        } else {//if(childType instanceof ParameterizedType) {
            returnClass = (Class<?>) ((ParameterizedType) returnType).getRawType();
        } //is there ever going to be something else?
        try {
            if (returnClass.equals(String.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Integer.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Long.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Float.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Double.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Character.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (returnClass.equals(Boolean.class)) {
                return currentSettings.get(new Pair<Method, Field>(method, f));
            } else if (Observable.class.isAssignableFrom(returnClass)) {
                Type containedClass = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                return Observable.from(createObject(containedClass, method,null, currentSettings));
            } else if (Collection.class.isAssignableFrom(returnClass)) {
                log("returnClass is collection " + returnClass.getSimpleName());
                List<Object> collection = new ArrayList<Object>();
                Type containedType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                log("adding three items to collection");
                if (containedType instanceof Class<?> && ((Class<?>) containedType).isAnnotationPresent(ListModder.class)) {
                    Class containedClass = (Class<?>) containedType;
                    ListModder builderAnnotation = (ListModder) containedClass.getAnnotation(ListModder.class);
                    IListModder builder = builderAnnotation.value().newInstance();
                    int count = builder.getCount();
                    for (int i = 0; i != count; i++) {
                        collection.add(createObject(containedType, method,f, currentSettings));
                    }
                    builder.modifyList(collection);
                } else {

                    //todo what happens when this List is supposed to contain a string? createObject won't handle it
                    //maybe that should be a special case?
                    //or should createObject be able to handle that?
                    //how would configuration work in that case?
                    //maybe that's what list options should be
                    //if it's a "primitive" list, itll have the same settings the primitives would have
                    //otherwise it has List settings?
                    collection.add(createObject(containedType, method,f, currentSettings));
                    collection.add(createObject(containedType, method,f, currentSettings));
                    collection.add(createObject(containedType, method,f, currentSettings));
                }

                return collection;
            } else { // this is a traditional model object, fill its fields
                Object response = returnClass.newInstance();
                Field[] childFields = returnClass.getDeclaredFields(); // todo add support for super classes here

                for (int i = 0; i != childFields.length; i++) {
                    //should the contents of this loop be replaced with a call to createObject?
                    //see todo right before the collection stuff


                    //this is an unreadable mess, fix it
                    Field childField = childFields[i];
                    Class fieldType = childField.getType();
                    if (fieldType.equals(String.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(Integer.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(int.class)) {
                        childField.setInt(response, (Integer) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else if (fieldType.equals(Long.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(long.class)) {
                        childField.setLong(response, (Long) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else if (fieldType.equals(Double.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(double.class)) {
                        childField.setDouble(response, (Double) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else if (fieldType.equals(Float.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(float.class)) {
                        childField.setFloat(response, (Float) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else if (fieldType.equals(Character.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(char.class)) {
                        childField.setChar(response, (Character) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else if (fieldType.equals(Boolean.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(boolean.class)) {
                        childField.setBoolean(response, (Boolean) currentSettings.get(new Pair<Method, Field>(method, childField)));
                    } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                        // may need to explicity state what children to add
                        childField.set(response, createObject(childField.getGenericType(), method, childField, currentSettings));

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
