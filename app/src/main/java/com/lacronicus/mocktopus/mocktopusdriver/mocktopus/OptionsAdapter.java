package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by fdoyle on 7/15/14.
 */
public class OptionsAdapter extends BaseAdapter {
    FlattenedOptions options;

    Context c;

    public OptionsAdapter(Context c) {
        this.c = c;

    }

    public void setContent(FlattenedOptions options) {
        this.options = options;
    }

    @Override
    public int getCount() {
        if(options == null)
            return 0;
        return options.itemList.size();
    }

    @Override
    public FlattenedOptions.FlatOptionsItem getItem(int position) {
        return options.itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t = new TextView(c);
        t.setText(options.itemList.get(position).getString());
        if(getItemViewType(position) == FlattenedOptions.FlatOptionsItem.TYPE_METHOD) {
            t.setTypeface(null, Typeface.BOLD);
        }
        return t;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }
}
