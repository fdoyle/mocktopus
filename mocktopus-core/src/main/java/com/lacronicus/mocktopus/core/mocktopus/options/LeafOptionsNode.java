package com.lacronicus.mocktopus.core.mocktopus.options;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/25/14.
 */
public class LeafOptionsNode implements IOptionsNode {
    List<Object> options;
    Method method; // the method this OptionsNode is being created for
    Field field; // the field this is going into
    Type layerType; // the type of the thing this represents


    //field may, in very rare circumstances, be null"
    public LeafOptionsNode(Method method, Field field, Type layerType, int depth) {
        options = new ArrayList<Object>();
        this.method = method;
        this.field = field;
        this.layerType = layerType;

        if (layerType.equals(String.class)) {
            options = FieldOptionsListBuilder.getOptionsForStringField(field);
        } else if (layerType.equals(Integer.class)) {
            options = FieldOptionsListBuilder.getOptionsforIntegerField(field, true);
        } else if (layerType.equals(int.class)) {
            options = FieldOptionsListBuilder.getOptionsforIntegerField(field, false);
        } else if (layerType.equals(Long.class)) {
            options = FieldOptionsListBuilder.getOptionsforLongField(field, true);
        } else if (layerType.equals(long.class)) {
            options = FieldOptionsListBuilder.getOptionsforLongField(field, false);
        } else if (layerType.equals(Double.class)) {
            options = FieldOptionsListBuilder.getOptionsforDoubleField(field, true);
        } else if (layerType.equals(double.class)) {
            options = FieldOptionsListBuilder.getOptionsforDoubleField(field, false);
        } else if (layerType.equals(Float.class)) {
            options = FieldOptionsListBuilder.getOptionsforFloatField(field, true);
        } else if (layerType.equals(float.class)) {
            options = FieldOptionsListBuilder.getOptionsforFloatField(field, false);
        } else if (layerType.equals(Character.class)) {
            options = FieldOptionsListBuilder.getOptionsforCharField(field, true);
        } else if (layerType.equals(char.class)) {
            options = FieldOptionsListBuilder.getOptionsforCharField(field, false);
        } else if (layerType.equals(Boolean.class)) {
            options = FieldOptionsListBuilder.getOptionsforBooleanField(field, true);
        } else if (layerType.equals(boolean.class)) {
            options = FieldOptionsListBuilder.getOptionsforBooleanField(field, false);
        }
    }

    @Override
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addField(method, field, layerType, options);
    }

    @Override
    public void addDefaultSettingsTo(FieldSettings toAdd) {
        toAdd.put(new Pair<Method, Field>(method, field), options.get(0));
    }
}
