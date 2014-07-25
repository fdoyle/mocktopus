package com.lacronicus.mocktopus.mocktopusdriver;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.FakeService;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyCollectionItemModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.RedditService;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.model.SubredditResponse;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity {

    @Inject
    RedditService redditService;

    @Inject
    FakeService fakeService;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        t = (TextView) findViewById(R.id.tv);
        View b = findViewById(R.id.open_config);
        fakeService.returnMyModelObservable().subscribe(new Action1<MyModel>() {
            @Override
            public void call(MyModel myModel) {
                t.setText("myModel response converted to json:\n" + gson.toJson(myModel));
            }
        });

        /*redditService.getSubreddit().subscribe(new Action1<SubredditResponse>() {
            @Override
            public void call(SubredditResponse subredditResponse) {
                t.setText(gson.toJson(subredditResponse));
                Linkify.addLinks(t, Linkify.ALL);
            }
        });*/

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
