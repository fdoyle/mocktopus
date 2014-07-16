package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 7/16/14.
 */
public class OptionsNode {

    enum LogLevel {
        FULL,
        NONE
    }

    LogLevel level = LogLevel.FULL;
    int depth;
    public Map<Field, List<Object>> fieldOptions;

    public Map<Field, OptionsNode> childOptions;
    Class layerClass;
    Method method;


    /**
     * @param layerClass the class this layer represents
     * @param depth      distance to "root" OptionsNode
     */
    public OptionsNode(Method m, Class layerClass, int depth) {
        log("new OptionsNode");
        this.method = m;
        this.layerClass = layerClass;
        fieldOptions = new HashMap<Field, List<Object>>();
        childOptions = new HashMap<Field, OptionsNode>();

        this.depth = depth;
        Field[] fields = layerClass.getDeclaredFields(); //getDeclaredFields? what's different?
        for (int i = 0; i != fields.length; i++) {
            Field field = fields[i];

            Class fieldType = field.getType();
            if (fieldType.equals(String.class)) {

                List<Object> optionsList = new FieldOptionsListBuilder().getOptionsForStringField(field); //keep a ref to this?

                log("adding field option for String " + field.getName());
                fieldOptions.put(field, optionsList);
                log("setting default for " + field.getName() + " to " + optionsList.get(0)); //clean up some


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
                childOptions.put(field, new OptionsNode(method, fieldType, depth + 1));
                log("adding field option for child Object" + field.getName());
            }

        }

    }

    public List<String> getFieldNames() {
        List<String> fieldNames = new ArrayList<String>();
        for (Field field : fieldOptions.keySet()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }


    //keep a constantly updated version in memory, so you dont have to flatten for each query?
    public Map<Field, Object> getFlattenedFieldSettings() {
        HashMap<Field, Object> allFieldSettings = new HashMap<Field, Object>();
        for (Field f : childOptions.keySet()) {
            OptionsNode node = childOptions.get(f);
            allFieldSettings.putAll(node.getFlattenedFieldSettings());
        }
        return allFieldSettings;
    }

    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addChildObject(layerClass);
        //add my fields
        for(Field f : fieldOptions.keySet()) {
            flattenedOptions.addField(method, f, fieldOptions.get(f));
        }


        //add child fields
        for(OptionsNode child : childOptions.values()) {
            child.addToFlattenedOptions(flattenedOptions);
        }

    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        for(Field f : fieldOptions.keySet()) {
            Object firstItem = fieldOptions.get(f).get(0);
            toAdd.put(new Pair<Method, Field>(method, f), firstItem);
        }
        for(OptionsNode node : childOptions.values()) {
            node.addDefaultSettingsTo(toAdd);
        }
    }

    private void log(String statement) {
        if (level.equals(LogLevel.FULL)) {
            Log.d(Tag.mainTag + this.getClass().getName(), statement);
        }
    }

}