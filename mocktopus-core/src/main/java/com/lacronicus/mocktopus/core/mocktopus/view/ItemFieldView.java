package com.lacronicus.mocktopus.core.mocktopus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.mocktopus.core.R;


/**
 * Created by fdoyle on 7/25/14.
 */
public class ItemFieldView extends LinearLayout {
    public TextView text;

    public ItemFieldView(Context context) {
        super(context);
    }

    public ItemFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemFieldView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = (TextView) findViewById(R.id.text);
    }
}
