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
        log("new SingleOptionsNode for method: " + m.getName() +" className: " +  layerClass.getSimpleName());
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

            if (field.getName().equals("this$0")) {
                //is there a better way to do this? does this even work?
                log("found 'this'");
            } else if (fieldType.equals(String.class)) {
                fieldOptions.put(field, b.getOptionsForStringField(field));
            } else if (fieldType.equals(Integer.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforIntegerField(field, true));
            } else if (fieldType.equals(int.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforIntegerField(field, false));
            } else if (fieldType.equals(Long.class)) {
                //todo
            } else if (fieldType.equals(Double.class)) {
                fieldOptions.put(field, b.getOptionsforDoubleField(field, true));
            } else if (fieldType.equals(double.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforDoubleField(field, false));
            } else if (fieldType.equals(Float.class)) {
                fieldOptions.put(field, b.getOptionsforFloatField(field, true));
            } else if (fieldType.equals(float.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforFloatField(field, false));
            } else if (fieldType.equals(Character.class)) {
                fieldOptions.put(field, b.getOptionsforCharField(field, true));
            } else if (fieldType.equals(char.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforCharField(field, false));
            } else if (fieldType.equals(Short.class)) {
                //todo
            } else if (fieldType.equals(Byte.class)) {
                //todo
            } else if (fieldType.equals(Boolean.class)) {
                fieldOptions.put(field, b.getOptionsforBooleanField(field, true));
            } else if (fieldType.equals(boolean.class)) { //ignore everything but string and child classes
                fieldOptions.put(field, b.getOptionsforBooleanField(field, false));
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                log("adding field option for Collection: " + field.getName() + " depth " + depth);
                ParameterizedType listParameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> listClass = (Class<?>) listParameterizedType.getActualTypeArguments()[0];//learn what's going on here
                //Collection collection = (Collection) field.get(response);
                childCollectionOptions.put(field, new CollectionOptionsNode(method, fieldType, listClass, depth + 1));
            } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                log("adding field option for child Object" + field.getName());
                childOptions.put(field, new SingleObjectOptionsNode(method, fieldType, depth + 1));
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