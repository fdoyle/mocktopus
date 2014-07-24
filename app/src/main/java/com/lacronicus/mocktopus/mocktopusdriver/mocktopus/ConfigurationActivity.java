package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.lacronicus.mocktopus.mocktopusdriver.BaseActivity;
import com.lacronicus.mocktopus.mocktopusdriver.R;

import javax.inject.Inject;

/**
 * Created by fdoyle on 7/10/14.
 * Allows the user to modify the current settings for an api
 */
public class ConfigurationActivity extends BaseActivity {

    @Inject
    Mocktopus mocktopus;
    MockInvocationHandler handler;

    ViewPager vp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_settings);
        vp = (ViewPager) findViewById(R.id.vp);
        ConfigFragmentAdapter adapter = new ConfigFragmentAdapter(getSupportFragmentManager());
        adapter.setContent(mocktopus);
        adapter.notifyDataSetChanged();
        vp.setAdapter(adapter);



        //Log.d("TAG","myService.returnMyModel().myString = " + myService.returnMyModel().myString);
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
