package com.lacronicus.mocktopus.mocktopusdriver;

import android.app.Application;
import android.util.Log;

import com.lacronicus.mocktopus.mocktopusdriver.dagger.AppModule;
import com.lacronicus.mocktopus.mocktopusdriver.dagger.IObjectGraph;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by fdoyle on 7/17/14.
 */
public class MainApp extends Application implements IObjectGraph {

    private static ObjectGraph applicationGraph;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getAppModule());
        applicationGraph.inject(this);
    }


    protected Object getAppModule() {
        return new AppModule(this);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return applicationGraph;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this)
        );
    }


    public void inject(Object object) {
        applicationGraph.inject(object);
    }
}
