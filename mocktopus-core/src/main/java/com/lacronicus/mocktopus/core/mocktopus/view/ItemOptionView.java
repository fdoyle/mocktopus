package com.lacronicus.mocktopus.core.mocktopus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.mocktopus.core.R;


/**
 * Created by fdoyle on 7/25/14.
 */
public class ItemOptionView extends LinearLayout {
    public TextView text;
    public ItemOptionView(Context context) {
        super(context);
    }

    public ItemOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemOptionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = (TextView) findViewById(R.id.text);
    }
}
