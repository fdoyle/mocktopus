package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created by fdoyle on 7/15/14.
 */
public class OptionsAdapter extends BaseExpandableListAdapter {
    FlattenedOptions options;

    Context c;

    public OptionsAdapter(Context c) {
        this.c = c;
    }

    public void setContent(FlattenedOptions options) {
        this.options = options;
    }


    @Override
    public int getGroupCount() {
        if (options == null)
            return 0;
        return options.itemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        switch (getGroupType(groupPosition)) {
            case FlattenedOptions.FlatOptionsItem.TYPE_METHOD:
                return 0;//todo
            case FlattenedOptions.FlatOptionsItem.TYPE_OBSERVABLE:
                return 0;//todo
            case FlattenedOptions.FlatOptionsItem.TYPE_COLLECTION:
                return 0;//todo
            case FlattenedOptions.FlatOptionsItem.TYPE_CHILD:
                return 0;//todo
            case FlattenedOptions.FlatOptionsItem.TYPE_FIELD:
                return getGroup(groupPosition).methodFieldItem.fieldOptions.size();
            case FlattenedOptions.FlatOptionsItem.TYPE_INVALID:
            default:
                return 0;
        }
    }

    @Override
    public FlattenedOptions.FlatOptionsItem getGroup(int groupPosition) {
        return options.itemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView t = new TextView(c);
        t.setText(options.itemList.get(groupPosition).getString());
        if (getGroupType(groupPosition) == FlattenedOptions.FlatOptionsItem.TYPE_METHOD) {
            t.setTypeface(null, Typeface.BOLD);
        }
        t.setPadding(80, 20, 20, 20);
        return t;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView t = new TextView(c);
        t.setPadding(20, 20, 20, 20);
        FlattenedOptions.FlatOptionsItem group = getGroup(groupPosition);
        switch (group.getType()) {
            case FlattenedOptions.FlatOptionsItem.TYPE_METHOD:
                break;
            case FlattenedOptions.FlatOptionsItem.TYPE_OBSERVABLE:
                break;
            case FlattenedOptions.FlatOptionsItem.TYPE_COLLECTION:
                break;
            case FlattenedOptions.FlatOptionsItem.TYPE_CHILD:
                break;
            case FlattenedOptions.FlatOptionsItem.TYPE_FIELD:
                Object childObject = group.methodFieldItem.fieldOptions.get(childPosition);
                t.setText(String.valueOf(childObject));
                break;
            case FlattenedOptions.FlatOptionsItem.TYPE_INVALID:
            default:
                t.setText("invalid");
                break;
        }
        return t;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupTypeCount() {
        return 5;

    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getGroupType(int groupPosition) {
        return getGroup(groupPosition).getType();
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }


}
