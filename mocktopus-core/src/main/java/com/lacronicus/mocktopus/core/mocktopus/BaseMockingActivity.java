package com.lacronicus.mocktopus.core.mocktopus;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

/**
 * Created by fdoyle on 7/15/14.
 */
public class BaseMockingActivity extends FragmentActivity {
    public static final int CONFIG_REQUEST_CODE = 999;




    public void showConfigScreen() {
        Intent i = new Intent(this, ConfigurationActivity.class);
        startActivityForResult(i, CONFIG_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CONFIG_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    recreate();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
