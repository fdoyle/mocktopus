package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
    MockInvocationHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_settings);

        final OptionsAdapter adapter = new OptionsAdapter(this);
        adapter.setContent(handler.getFlattenedOptions());
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlattenedOptions.FlatOptionsItem item = adapter.getItem(position);

                switch (item.getType()){
                    case FlattenedOptions.FlatOptionsItem.TYPE_METHOD:
                        toast("method");
                        break;
                    case FlattenedOptions.FlatOptionsItem.TYPE_CHILD:
                        toast("child");
                        break;
                    case FlattenedOptions.FlatOptionsItem.TYPE_FIELD:
                        toast("currently:\n\"" + handler.getSettings().get(item.methodFieldItem.getPair()).toString() + "\"");

                }
            }
        });


        //Log.d("TAG","myService.returnMyModel().myString = " + myService.returnMyModel().myString);
    }
    //todo inject options;

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
