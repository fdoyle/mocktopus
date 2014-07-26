package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.mocktopus.mocktopusdriver.R;

/**
 * Created by fdoyle on 7/25/14.
 */
public class ItemMethodView extends LinearLayout {
    public TextView text;
    public ItemMethodView(Context context) {
        super(context);
    }

    public ItemMethodView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemMethodView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = (TextView) findViewById(R.id.text);
    }
}
