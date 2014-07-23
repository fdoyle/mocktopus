package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
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
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                toast("child clicked, change the value!");
                FieldSettings settings = handler.getSettings();
                switch (adapter.getGroup(groupPosition).getType()) {
                    case FlattenedOptions.FlatOptionsItem.TYPE_FIELD:
                        FlattenedOptions.MethodFieldItem item = adapter.getGroup(groupPosition).methodFieldItem; //currently the
                        settings.put(item.getPair(), item.fieldOptions.get(childPosition));
                        break;
                    default:
                        break;

                }


                return true;
            }
        });


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
