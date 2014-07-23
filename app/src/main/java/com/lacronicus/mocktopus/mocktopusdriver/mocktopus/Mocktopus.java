package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Proxy;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Mocktopus <T> {

    public T service;
    public MockInvocationHandler handler;

    public Mocktopus(Class api) {
        handler = new MockInvocationHandler(api);

        //todo make this generic
        service = (T) Proxy.newProxyInstance(
                api.getClassLoader(),
                new Class[]{api}, // is this right?
                handler
        );
    }


    //this one should work as well, and be more flexible, but let's hold off on it until the rest works?
    /*public <T> T build(Class<T> api) {
        InvocationHandler apiHandler = new MockInvocationHandler(api);

        T service = (T) Proxy.newProxyInstance(
                ApiService.class.getClassLoader(),
                new Class[] {api}, // is this right?
                apiHandler
                );
        return service;
    }*/

    public Mocktopus build() {
        //finalize object
        return this;
    }

    public MockInvocationHandler getHandler() {
        return handler;
    }

    public T getService() {
        return service;
    }

    public void addErrorState(String stateName, Class toReturn) {
        //stub
    }
}
