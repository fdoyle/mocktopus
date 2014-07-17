package com.lacronicus.mocktopus.mocktopusdriver;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.ApiService;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

    @Inject
    ApiService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myService.returnMyModel();
        //myService.returnMyCollectionContainingModel();
        myService.returnMyModelArrayList();
       // myService.returnMyModelList();
        Button b = (Button) findViewById(R.id.open_config);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfigScreen();
            }
        });
    }


    public void toast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

}
