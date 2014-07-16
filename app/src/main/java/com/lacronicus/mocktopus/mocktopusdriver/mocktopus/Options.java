package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by fdoyle on 7/10/14.
 * keeps track of available options for field/method pairs
 * as well as the currently selected option
 * <p/>
 * <p/>
 * <p/>
 * only supports strings in first release
 */
public class Options {
    enum LogLevel {
        FULL,
        NONE
    }

    Map<Method, OptionsNode> methodOptions;
    LogLevel level = LogLevel.FULL;

    public Options(Class classToBuild) {
        methodOptions = new HashMap<Method, OptionsNode>();

        Method[] methods = classToBuild.getMethods();
        for (int i = 0; i != methods.length; i++) {
            Method method = methods[i];
            Class methodReturnClass = method.getReturnType();
            log("creating new base OptionsNode for method " + method.getName());
            methodOptions.put(method, new OptionsNode(method, methodReturnClass, 0));
        }
    }

    public void setLogLevel(LogLevel level) {
        //todo
    }


    //call this recursively
    public <T> T createObject(Class<T> returnClass, Method method, FieldSettings currentSettings) {
        log("creating a new object");
        try {
            T response = returnClass.newInstance();
            Field[] fields = returnClass.getDeclaredFields(); // todo add support for super classes here
            for (int i = 0; i != fields.length; i++) {
                Field field = fields[i];
                Class fieldType = field.getType();
                if (fieldType.equals(String.class)) {
                    log("loading " + currentSettings.get(new Pair<Method, Field>(method, field)) + " into " + field.getName());
                    field.set(response, currentSettings.get(new Pair<Method, Field>(method, field)));
                } else if (fieldType.equals(Integer.class)) { //ignore everything but string and child classes
                    //todo
                } else if (fieldType.equals(Long.class)) {
                    //todo
                } else if (fieldType.equals(Double.class)) {
                    //todo
                } else if (fieldType.equals(Float.class)) {
                    //todo
                } else if (fieldType.equals(Character.class)) {
                    //todo
                } else if (fieldType.equals(Short.class)) {
                    //todo
                } else if (fieldType.equals(Byte.class)) {
                    //todo
                } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                    // may need to explicity state what children to add
                    // what does Gson do? derp, it knows because the json already has structure, not because of any special knowledge
                    // about the fields. hmm...

                    log("loading new object into " + field.getName());
                    field.set(response, createObject(fieldType, method, currentSettings));

                }
            }

            if(response == null) {
                log("response is null");
            } else {
                log("response is not null");
            }
            return response;
        } catch (InstantiationException e) {
            log("error");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log("error");
            e.printStackTrace();
        }

        log("failed to create object, returning null");
        return null;
    }

    public FlattenedOptions flatten() {
        FlattenedOptions flattenedOptions = new FlattenedOptions();
       Set<Method> methodSet =  methodOptions.keySet();
        for(Method method : methodSet) {
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
        for(OptionsNode node : methodOptions.values()) {
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
