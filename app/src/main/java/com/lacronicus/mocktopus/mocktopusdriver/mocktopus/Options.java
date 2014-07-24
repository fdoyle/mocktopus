package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection.ListBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.builder.IListBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.POST;
import retrofit.http.PUT;
import rx.Observable;

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
            if (Observable.class.isAssignableFrom(returnClass)) {
                log("return type is observable: " + returnClass.getSimpleName());
                ParameterizedType methodReturnType = (ParameterizedType) method.getGenericReturnType(); //this works for things that are List<Object>
                Type observableType = methodReturnType.getActualTypeArguments()[0];//learn what's going on here

                methodOptions.put(method, new ObservableOptionsNode(method, methodReturnType, observableType, 0));


            } else if (Collection.class.isAssignableFrom(returnClass)) { // todo might be a List<Object> or might be something that extends List<Object>
                log("return type is collection: " + returnClass.getSimpleName());

                ParameterizedType methodReturnType = (ParameterizedType) method.getGenericReturnType(); //this works for things that are List<Object>

                //ParameterizedType methodReturnClass = (ParameterizedType) method.getReturnType().getGenericSuperclass();//works on things that extend List<Object>
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

    public FlattenedOptions flatten() {
        FlattenedOptions flattenedOptions = new FlattenedOptions();
        Set<Method> methodSet = methodOptions.keySet();
        for (Method method : methodSet) {
            //add method to flattenedOptions
            String endpoint = "";
            if (method.getAnnotation(GET.class) != null)
                endpoint += method.getAnnotation(GET.class).value();
            if (method.getAnnotation(POST.class) != null)
                endpoint += method.getAnnotation(POST.class).value();
            if (method.getAnnotation(PUT.class) != null)
                endpoint += method.getAnnotation(PUT.class).value();
            if (method.getAnnotation(DELETE.class) != null)
                endpoint += method.getAnnotation(DELETE.class).value();
            if (method.getAnnotation(HEAD.class) != null)
                endpoint += method.getAnnotation(HEAD.class).value();

            flattenedOptions.addMethod(method, endpoint);
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


    private static void log(String statement) {
            Log.d(Tag.mainTag + Options.class.getName(), statement);
    }


}
