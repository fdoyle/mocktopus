package com.lacronicus.mocktopus.mocktopusdriver;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.FakeService;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyCollectionContainingModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.RedditService;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity {

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
        View b = findViewById(R.id.open_config);
        fakeService.returnStringList().subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                setText(strings);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfigScreen();
            }
        });

    }

    public void setText(Object o) {
        t.setText("myModel response converted to json:\n" + gson.toJson(o));
        Linkify.addLinks(t, Linkify.ALL);
    }

    public void toast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

}
