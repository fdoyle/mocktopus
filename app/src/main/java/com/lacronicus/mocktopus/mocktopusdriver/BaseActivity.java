package com.lacronicus.mocktopus.mocktopusdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lacronicus.mocktopus.core.mocktopus.Mocktopus;

/**
 * Created by fdoyle on 7/17/14.
 */
public class BaseActivity extends FragmentActivity{
    public static final int REQUEST_CODE = -10001;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        ((MainApp) getApplication()).inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Mocktopus.onActivityResult(this, requestCode, resultCode, data);
    }
}
