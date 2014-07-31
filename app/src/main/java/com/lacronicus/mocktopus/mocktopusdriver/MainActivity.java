package com.lacronicus.mocktopus.mocktopusdriver;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.mocktopus.core.mocktopus.Mocktopus;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.FakeService;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.RedditService;
import com.squareup.seismic.ShakeDetector;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity implements ShakeDetector.Listener {

    @Inject
    RedditService redditService;

    @Inject
    FakeService fakeService;

    TextView t;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new GsonBuilder().setPrettyPrinting().create();
        t = (TextView) findViewById(R.id.tv);
        /*fakeService.returnStringList().subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                setText(strings);
            }
        });*/
        fakeService.returnMyModelObservable().subscribe(new Action1<MyModel>() {
            @Override
            public void call(MyModel myModel) {
                setText(myModel);
            }
        });
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setText(Object o) {
        t.setText("myModel response converted to json:\n" + gson.toJson(o));
        Linkify.addLinks(t, Linkify.ALL);
    }

    @Override
    public void hearShake() {
        Mocktopus.showConfigScreen(this);
    }

}
