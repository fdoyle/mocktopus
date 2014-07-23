package com.lacronicus.mocktopus.mocktopusdriver;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.ApiService;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity {

    @Inject
    ApiService myService;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.tv);
//        myService.returnMyModel();
//        myService.returnMyCollectionContainingModel();
//        myService.returnMyModelArrayList();
//        myService.returnMyModelList();
//        myService.returnMyModelListList();
        myService.returnMyModelObservable().subscribe(new Action1<MyModel>() {
            @Override
            public void call(MyModel myModel) {
                t.setText("mymodel.myString = " + myModel.myString);
            }
        });
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
