package com.lacronicus.mocktopus.mocktopusdriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.BaseMockingActivity;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.ConfigurationActivity;

/**
 * Created by fdoyle on 7/17/14.
 */
public class BaseActivity extends BaseMockingActivity {
    public static final int REQUEST_CODE = -10001;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        ((MainApp) getApplication()).inject(this);
    }

}
