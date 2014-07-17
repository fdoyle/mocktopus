package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.ApiService;

import java.lang.reflect.Proxy;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Mocktopus {

    public ApiService service;
    public MockInvocationHandler handler;

    public Mocktopus(Class api) {
        handler = new MockInvocationHandler(api);

        service = (ApiService) Proxy.newProxyInstance(
                ApiService.class.getClassLoader(),
                new Class[]{api}, // is this right?
                handler
        );
    }


    //this one should work as well, and be more flexible, but let's hold off on it until the rest works?
    /*public <T> T build(Class<T> api) {
        InvocationHandler apiHandler = new MockInvocationHandler();

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

    public ApiService getService() {
        return service;
    }

    public void addErrorState(String stateName, Class toReturn) {
        //stub
    }
}
