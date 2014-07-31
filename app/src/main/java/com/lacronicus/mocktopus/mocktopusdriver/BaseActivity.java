package com.lacronicus.mocktopus.mocktopusdriver;

import android.os.Bundle;

import com.lacronicus.mocktopus.core.mocktopus.BaseMockingActivity;
import com.lacronicus.mocktopus.core.mocktopus.ConfigurationActivity;

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
