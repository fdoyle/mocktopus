package com.lacronicus.mocktopus.core.mocktopus.options;

import android.util.Log;

import com.lacronicus.mocktopus.core.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.Tag;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fdoyle on 7/16/14.
 */
public class ModelOptionsNode implements IOptionsNode {

    enum LogLevel {
        FULL,
        NONE
    }

    LogLevel level = LogLevel.FULL;
    int depth;
    //public Map<Field, List<Object>> fieldOptions;
    public Map<Field, LeafOptionsNode> fieldOptions;
    public Map<Field, ModelOptionsNode> childOptions;
    public Map<Field, CollectionOptionsNode> childCollectionOptions;
    Class layerClass;
    Method method;


    /**
     * @param layerClass the class this layer represents
     * @param depth      distance to "root" OptionsNode
     */
    public ModelOptionsNode(Method m, Class layerClass, int depth) {
        log("new SingleOptionsNode for method: " + m.getName() +" className: " +  layerClass.getSimpleName());
        this.method = m;
        this.layerClass = layerClass;
        //fieldOptions = new HashMap<Field, List<Object>>();
        fieldOptions = new HashMap<Field, LeafOptionsNode>();
        childOptions = new HashMap<Field, ModelOptionsNode>();
        childCollectionOptions = new HashMap<Field, CollectionOptionsNode>();

        this.depth = depth;
        Field[] fields = layerClass.getDeclaredFields();
        FieldOptionsListBuilder b = new FieldOptionsListBuilder();
        //what if this is the "leaf" object? like a String or an Integer

        for (int i = 0; i != fields.length; i++) {
            Field field = fields[i];

            Class fieldType = field.getType();

            if (field.getName().equals("this$0")) {
                //is there a better way to do this? does this even work?
                log("found 'this,' probably a non-static inner class. don't use those in your models, things break");
            } else if (fieldType.equals(String.class)) {
                //fieldOptions.put(field, b.getOptionsForStringField(field));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Integer.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforIntegerField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(int.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforIntegerField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Long.class)) {
                //fieldOptions.put(field, b.getOptionsforLongField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(long.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforLongField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Double.class)) {
                //fieldOptions.put(field, b.getOptionsforDoubleField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(double.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforDoubleField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Float.class)) {
                //fieldOptions.put(field, b.getOptionsforFloatField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(float.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforFloatField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Character.class)) {
                //fieldOptions.put(field, b.getOptionsforCharField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(char.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforCharField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(Boolean.class)) {
                //fieldOptions.put(field, b.getOptionsforBooleanField(field, true));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (fieldType.equals(boolean.class)) { //ignore everything but string and child classes
                //fieldOptions.put(field, b.getOptionsforBooleanField(field, false));
                fieldOptions.put(field, new LeafOptionsNode(method, field, fieldType, depth+1));
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                log("adding field option for Collection: " + field.getName() + " depth " + depth);
                ParameterizedType listParameterizedType = (ParameterizedType) field.getGenericType();
                childCollectionOptions.put(field, new CollectionOptionsNode(method, field, listParameterizedType, depth + 1));
            } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                log("adding field option for child Object" + field.getName());
                childOptions.put(field, new ModelOptionsNode(method, fieldType, depth + 1));
            }

        }

    }



    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addChildObject(layerClass);
        //add my fields
        /*for (Field f : fieldOptions.keySet()) {
            flattenedOptions.addField(method, f, fieldOptions.get(f));
        }*/

        for(LeafOptionsNode f : fieldOptions.values()) {
            f.addToFlattenedOptions(flattenedOptions);
        }

        //add child fields
        for (ModelOptionsNode child : childOptions.values()) {
            child.addToFlattenedOptions(flattenedOptions);
        }

        //add collection fields
        for(CollectionOptionsNode collectionNode : childCollectionOptions.values()) {
            collectionNode.addToFlattenedOptions(flattenedOptions);
        }
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        for(LeafOptionsNode node : fieldOptions.values()) {
            node.addDefaultSettingsTo(toAdd);
        }
        for (ModelOptionsNode node : childOptions.values()) {
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