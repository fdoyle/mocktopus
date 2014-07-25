package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.options;

import android.util.Log;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.Tag;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
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
                methodOptions.put(method, new CollectionOptionsNode(method, null, methodReturnType, 0));
            } else {
                methodOptions.put(method, new ModelOptionsNode(method, returnClass, 0));
            }
        }
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
