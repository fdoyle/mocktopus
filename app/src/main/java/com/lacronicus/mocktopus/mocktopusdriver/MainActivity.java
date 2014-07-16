package com.lacronicus.mocktopus.mocktopusdriver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.Mocktopus;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.OptionsAdapter;
import com.lacronicus.mocktopus.mocktopusdriver.service.ApiService;


public class MainActivity extends Activity {

    ApiService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Mocktopus mocktopus = new Mocktopus(ApiService.class);
        myService = mocktopus.getService();

        final OptionsAdapter adapter = new OptionsAdapter(this);
        adapter.setContent(mocktopus.getHandler().getFlattenedOptions());
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
                        toast("currently:\n\"" + mocktopus.getHandler().getSettings().get(item.methodFieldItem.getPair()).toString() + "\"");

                }
            }
        });


        Log.d("TAG","myService.returnMyModel().myString = " + myService.returnMyModel().myString);
    }

    public void toast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }
}
