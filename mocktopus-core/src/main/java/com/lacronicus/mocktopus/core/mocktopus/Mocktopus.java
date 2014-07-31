package com.lacronicus.mocktopus.core.mocktopus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Mocktopus {
    public static final int CONFIG_REQUEST_CODE = 999;

    static Mocktopus mocktopus;

    public Map<Type, Object> services;

    public Map<Type, MockInvocationHandler> handlers;

    private Mocktopus() {
        handlers = new HashMap<Type, MockInvocationHandler>();
        services = new HashMap<Type, Object>();
    }


    public static Mocktopus getInstance() {
        if(mocktopus == null) {
            mocktopus = new Mocktopus();
        }
        return mocktopus;
    }

    public <T> T initApi(Class<T> api) {

        MockInvocationHandler handler = new MockInvocationHandler(api);
        T service = (T) Proxy.newProxyInstance(
                api.getClassLoader(),
                new Class[]{api}, // is this right?
                handler
        );

        services.put(api,service);
        handlers.put(api, handler);
        return service;
    }


    public MockInvocationHandler getHandler(Type apiType) {
        return handlers.get(apiType);
    }

    public void addErrorState(String stateName, Class toReturn) {
        //todo
    }

    public int getApiCount() {
        return services.size();
    }

    public Set<Type> getApiSet() {
        return services.keySet();
    }

    public static void showConfigScreen(Activity activity) {
        Intent i = new Intent(activity, ConfigurationActivity.class);
        activity.startActivityForResult(i, CONFIG_REQUEST_CODE);
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONFIG_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    activity.recreate();
                }
                break;
        }
    }

}
