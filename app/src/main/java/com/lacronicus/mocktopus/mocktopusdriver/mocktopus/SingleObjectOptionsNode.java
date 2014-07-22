package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;
import android.util.Pair;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 7/16/14.
 */
public class SingleObjectOptionsNode implements IOptionsNode{

    enum LogLevel {
        FULL,
        NONE
    }

    LogLevel level = LogLevel.FULL;
    int depth;
    public Map<Field, List<Object>> fieldOptions;
    public Map<Field, SingleObjectOptionsNode> childOptions;
    public Map<Field, CollectionOptionsNode> childCollectionOptions;
    Class layerClass;
    Method method;


    /**
     * @param layerClass the class this layer represents
     * @param depth      distance to "root" OptionsNode
     */
    public SingleObjectOptionsNode(Method m, Class layerClass, int depth) {
        log("new OptionsNode");
        this.method = m;
        this.layerClass = layerClass;
        fieldOptions = new HashMap<Field, List<Object>>();
        childOptions = new HashMap<Field, SingleObjectOptionsNode>();
        childCollectionOptions = new HashMap<Field, CollectionOptionsNode>();

        this.depth = depth;
        Field[] fields = layerClass.getDeclaredFields();
        FieldOptionsListBuilder b = new FieldOptionsListBuilder();
        for (int i = 0; i != fields.length; i++) {
            Field field = fields[i];

            Class fieldType = field.getType();


            if (fieldType.equals(String.class)) {
                fieldOptions.put(field, b.getOptionsForStringField(field));
            } else if (fieldType.equals(Integer.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforIntegerField(field));
            } else if (fieldType.equals(Long.class)) {
                //todo
            } else if (fieldType.equals(Double.class)) {
                fieldOptions.put(field, b.getOptionsforDoubleField(field));
            } else if (fieldType.equals(Float.class)) {
                fieldOptions.put(field, b.getOptionsforFloatField(field));
            } else if (fieldType.equals(Character.class)) {
                fieldOptions.put(field, b.getOptionsforCharField(field));
            } else if (fieldType.equals(Short.class)) {
                //todo
            } else if (fieldType.equals(Byte.class)) {
                //todo
            } else if (fieldType.equals(Boolean.class)) {
                fieldOptions.put(field, b.getOptionsforBooleanField(field));
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                log("adding field option for Collection");
                ParameterizedType listParameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> listClass = (Class<?>) listParameterizedType.getActualTypeArguments()[0];//learn what's going on here
                //Collection collection = (Collection) field.get(response);
                childCollectionOptions.put(field, new CollectionOptionsNode(method, fieldType, listClass, depth + 1));

            } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                childOptions.put(field, new SingleObjectOptionsNode(method, fieldType, depth + 1));
                log("adding field option for child Object" + field.getName());
            }

        }

    }



    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addChildObject(layerClass);
        //add my fields
        for (Field f : fieldOptions.keySet()) {
            flattenedOptions.addField(method, f, fieldOptions.get(f));
        }

        //add child fields
        for (SingleObjectOptionsNode child : childOptions.values()) {
            child.addToFlattenedOptions(flattenedOptions);
        }

        //add collection fields
        for(CollectionOptionsNode collectionNode : childCollectionOptions.values()) {
            collectionNode.addToFlattenedOptions(flattenedOptions);
        }
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        for (Field f : fieldOptions.keySet()) {
            Object firstItem = fieldOptions.get(f).get(0);
            toAdd.put(new Pair<Method, Field>(method, f), firstItem);
        }
        for (SingleObjectOptionsNode node : childOptions.values()) {
            node.addDefaultSettingsTo(toAdd);
        }
        for (CollectionOptionsNode node : childCollectionOptions.values()) {
            node.addDefaultSettingsTo(toAdd);
        }
    }

    private void log(String statement) {
        if (level.equals(LogLevel.FULL)) {
            Log.d(Tag.mainTag + this.getClass().getName(), statement);
        }
    }

}