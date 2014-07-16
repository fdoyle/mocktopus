package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.util.Log;

import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fdoyle on 7/10/14.
 */
public class MockInvocationHandler implements InvocationHandler {

    Options options;


    public MockInvocationHandler(Class api) {
        options = new Options(api);
        //use api to build options
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //ignore args, proxy is "dumb"
        Log.d("TAG", "invoking some stuff");
        return options.createResponse(method, method.getReturnType());
    }
}
