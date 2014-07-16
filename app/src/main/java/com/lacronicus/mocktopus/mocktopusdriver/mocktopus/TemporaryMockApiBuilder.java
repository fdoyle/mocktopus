package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import com.lacronicus.mocktopus.mocktopusdriver.service.ApiService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by fdoyle on 7/10/14.
 */
public class TemporaryMockApiBuilder {

    public TemporaryMockApiBuilder() {

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

    public ApiService build(Class api) {
        InvocationHandler apiHandler = new MockInvocationHandler(api);

        ApiService service = (ApiService) Proxy.newProxyInstance(
                ApiService.class.getClassLoader(),
                new Class[]{api}, // is this right?
                apiHandler
        );
        return service;
    }

    /**
     * add a global error state. these are used as alternatives to "success" responses
     */
    public void addErrorState(String stateName, Class toReturn) {
        //stub
    }
}
