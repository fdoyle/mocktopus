package com.lacronicus.mocktopus.mocktopusdriver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.Mocktopus;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.OptionsAdapter;
import com.lacronicus.mocktopus.mocktopusdriver.service.ApiService;


public class MainActivity extends Activity {

    ApiService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mocktopus mocktopus = new Mocktopus(ApiService.class);
        myService = mocktopus.getService();

        OptionsAdapter adapter = new OptionsAdapter(this);
        adapter.setContent(mocktopus.getHandler().getFlattenedOptions());
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);


        Log.d("TAG","Result of interface call: " + myService.doStuff().myString);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
