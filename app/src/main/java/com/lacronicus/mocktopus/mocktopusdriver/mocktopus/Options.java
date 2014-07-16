package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 7/10/14.
 * keeps track of available options for field/method pairs
 * as well as the currently selected option
 *
 *
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
            methodOptions.put(method, new OptionsNode(methodReturnClass, 0));
        }
    }

    public void setLogLevel(LogLevel level) {
        //stub
    }


    public <T> T createResponse(Method method, Class<T> returnClass) { // how should exceptions be handled here?
        //where to handle methods that return observables/use callbacks
        OptionsNode node = methodOptions.get(method);
        return createObject(returnClass, node.getFlattenedFieldSettings());
    }

    //call this recursively
    public <T> T createObject(Class<T> returnClass, Map<Field, Object> fieldSettings) {
        try {
            T response = returnClass.newInstance();
            Field [] fields = returnClass.getDeclaredFields(); // todo add support for super classes here
            for(int i = 0; i != fields.length; i++) {
                Field field = fields[i];
                Class fieldType = field.getType();
                if (fieldType.equals(String.class)) {
                    log("loading " + fieldSettings.get(field) + " into " + field.getName());
                    field.set(response, fieldSettings.get(field));
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
                    field.set(response, createObject(fieldType, fieldSettings));

                }
            }




            return response;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map of classes fields to their available choices
     * <p/>
     * child objects get their own OptionsNode
     */
    public class OptionsNode {
        int depth;
        public Map<Field, List<String>> stringFieldOptions; // todo any reason this can't have a List<Object>?
                                                            // then we dont have to have a separate one for each type
                                                            // might even be able to combine with objectFieldOptions
        public Map<Field, Object> fieldSettings;
        //todo add more field types eg int, long, etc

        public Map<Field, OptionsNode> objectFieldOptions;


        /**
         * @param layerClass the class this layer represents
         * @param depth      distance to "root" OptionsNode
         */
        public OptionsNode(Class layerClass, int depth) {
            log("new OptionsNode");
            stringFieldOptions = new HashMap<Field, List<String>>();
            objectFieldOptions = new HashMap<Field, OptionsNode>();
            fieldSettings = new HashMap<Field, Object>();

            this.depth = depth;
            Field[] fields = layerClass.getFields(); //getDeclaredFields? what's different?
            for (int i = 0; i != fields.length; i++) {
                Field field = fields[i];

                Class fieldType = field.getType();
                if (fieldType.equals(String.class)) {

                    List<String> optionsList = new FieldOptionsListBuilder().getOptionsForStringField(field); //keep a ref to this?

                    log("adding field option for " + field.getName());
                    stringFieldOptions.put(field, optionsList);
                    log("setting default for " + field.getName() + " to " + optionsList.get(0)); //clean up some
                    fieldSettings.put(field, optionsList.get(0)); //might fail if empty?


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
                    objectFieldOptions.put(field, new OptionsNode(fieldType, depth + 1));
                    log("adding field option for " + field.getName());
                }

            }

        }

        public List<String> getFieldNames() {
            List<String> fieldNames = new ArrayList<String>();
            for (Field field : stringFieldOptions.keySet()) {
                fieldNames.add(field.getName());
            }
            return fieldNames;
        }


        //keep a constantly updated version in memory, so you dont have to flatten for each query?
        public Map<Field, Object> getFlattenedFieldSettings() {
            HashMap<Field, Object> allFieldSettings = new HashMap<Field, Object>();
            allFieldSettings.putAll(fieldSettings);
            for(Field f : objectFieldOptions.keySet()) {
                OptionsNode node = objectFieldOptions.get(f);
                allFieldSettings.putAll(node.getFlattenedFieldSettings());
            }
            return allFieldSettings;
        }

    }

    private void log(String statement) {
        if (level.equals(LogLevel.FULL) ){
            Log.d(Tag.mainTag + this.getClass().getName(), statement);
        }
    }


}
