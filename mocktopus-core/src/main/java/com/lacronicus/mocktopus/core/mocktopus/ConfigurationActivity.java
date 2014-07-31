package com.lacronicus.mocktopus.core.mocktopus;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.lacronicus.mocktopus.core.R;

/**
 * Created by fdoyle on 7/10/14.
 * Allows the user to modify the current settings for an api
 */
public class ConfigurationActivity extends FragmentActivity {

    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_settings);
        vp = (ViewPager) findViewById(R.id.vp);
        ConfigFragmentAdapter adapter = new ConfigFragmentAdapter(getSupportFragmentManager());
        adapter.setContent(Mocktopus.getInstance());
        adapter.notifyDataSetChanged();
        vp.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
