package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fdoyle on 7/10/14.
 * Represents the options for entire interface
 */
public class Options {
    enum LogLevel {
        FULL,
        NONE
    }

    Map<Method, IOptionsNode> methodOptions;
    LogLevel level = LogLevel.FULL;

    public Options(Class classToBuild) {
        methodOptions = new HashMap<Method, IOptionsNode>();

        Method[] methods = classToBuild.getMethods();
        for (int i = 0; i != methods.length; i++) {

            Method method = methods[i];
            log("creating new base OptionsNode for method " + method.getName());
            Class returnClass = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnClass)) { // todo might be a List<Object> or might be something that extends List<Object>
                log("return type is collection: " + returnClass.getSimpleName());

                ParameterizedType methodReturnType = (ParameterizedType) method.getGenericReturnType(); //this works for things that are List<Object>

                //ParameterizedType methodReturnClass = (ParameterizedType) method.getReturnType().getGenericSuperclass();//works on things that extend List<Object>

                // todo resolve this
                //this might be a parameterizedType

                Type listType = methodReturnType.getActualTypeArguments()[0];//learn what's going on here
                methodOptions.put(method, new CollectionOptionsNode(method, methodReturnType, listType, 0));
            } else {
                methodOptions.put(method, new SingleObjectOptionsNode(method, returnClass, 0));
            }
        }
    }

    public void setLogLevel(LogLevel level) {
        //todo
    }


    //call this recursively
    public Object createObject(Type returnType, Method method, FieldSettings currentSettings) {
        log("creating a new object");
        Class<?> returnClass;
        if(returnType instanceof Class) {
            returnClass = (Class<?>) returnType;
        } else {//if(childType instanceof ParameterizedType) {
            returnClass = (Class<?>) ((ParameterizedType) returnType).getRawType();
        }
        try {
            //if this new thing is a collection, make a collection and add children

            //if it's a "plain" object, make it and fill in its fields
            /*if(Observable.class.isAssignableFrom(returnClass)) {




            } else */if (Collection.class.isAssignableFrom(returnClass)) {
                List<Object> collection = new ArrayList<Object>();
                Type containedClass = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                log("adding three items to collection");
                collection.add(createObject(containedClass, method, currentSettings));
                collection.add(createObject(containedClass, method, currentSettings));
                collection.add(createObject(containedClass, method, currentSettings));

                return collection;
            } else {
                Object response = returnClass.newInstance();
                Field[] fields = returnClass.getDeclaredFields(); // todo add support for super classes here

                for (int i = 0; i != fields.length; i++) {
                    Field field = fields[i];
                    Class fieldType = field.getType();
                    if (fieldType.equals(String.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Integer.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Long.class)) {
                        //todo
                    } else if (fieldType.equals(Double.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Float.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Character.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else if (fieldType.equals(Short.class)) {
                        //todo
                    } else if (fieldType.equals(Byte.class)) {
                        //todo
                    } else if (fieldType.equals(Boolean.class)) {
                        field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                    } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                        // may need to explicity state what children to add
                        // what does Gson do? derp, it knows because the json already has structure, not because of any special knowledge
                        // about the fields. hmm...

                        log("loading new object into " + field.getName());
                        field.set(response, createObject(fieldType, method, currentSettings));

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

    public FlattenedOptions flatten() {
        FlattenedOptions flattenedOptions = new FlattenedOptions();
        Set<Method> methodSet = methodOptions.keySet();
        for (Method method : methodSet) {
            //add method to flattenedOptions
            flattenedOptions.addMethod(method, method.getName()); //todo change to endpoint name
            //add fields to flattenedOptions
            methodOptions.get(method).addToFlattenedOptions(flattenedOptions);
        }

        log("done flattening, contains " + flattenedOptions.itemList.size() + " items");
        return flattenedOptions;
    }


    public FieldSettings getDefaultFieldSettings() {
        FieldSettings settings = new FieldSettings();
        for (IOptionsNode node : methodOptions.values()) {
            node.addDefaultSettingsTo(settings);
        }
        return settings;
    }


    private void log(String statement) {
        if (level.equals(LogLevel.FULL)) {
            Log.d(Tag.mainTag + this.getClass().getName(), statement);
        }
    }


}
