package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Mocktopus {

    //public T service;
    public Map<Type, Object> services;

    //public MockInvocationHandler handler;
    public Map<Type, MockInvocationHandler> handlers;

    public Mocktopus() {
        /*handler = new MockInvocationHandler(api);*/
        handlers = new HashMap<Type, MockInvocationHandler>();


        //todo make this generic
        services = new HashMap<Type, Object>();
        /*service = (T) Proxy.newProxyInstance(
                api.getClassLoader(),
                new Class[]{api}, // is this right?
                handler
        );*/
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
        //stub
    }

    public int getApiCount() {
        return services.size();
    }

    public Set<Type> getApiSet() {
        return services.keySet();
    }
}
