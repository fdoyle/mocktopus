package com.lacronicus.mocktopus.mocktopusdriver.dagger;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.lacronicus.mocktopus.mocktopusdriver.MainActivity;
import com.lacronicus.mocktopus.mocktopusdriver.MainApp;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.ApiService;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.ConfigurationActivity;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.MockInvocationHandler;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.Mocktopus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(injects = {
        Injector.class,
        MainApp.class,
        MainActivity.class,
        ConfigurationActivity.class
}, library = true, complete = true)
public class AppModule {
    private final Application application;
    private final Injector injector;

    public AppModule(Application application) {
        this.application = application;
        this.injector = new Injector(application);
    }

    /**
     * Allow the application context to be injected but require that it be annotated with {@link
     * ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Injector providesInjector() {
        return injector;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Mocktopus<ApiService> provideMocktopus() { //todo what happens if there are multiple apis?
        return new Mocktopus<ApiService>(ApiService.class);
    }

    @Provides
    @Singleton
    ApiService provideApiService(Mocktopus<ApiService> mocktopus) {
        return mocktopus.getService();
    }

    @Provides
    @Singleton
    MockInvocationHandler provideHandler(Mocktopus<ApiService> mocktopus) {
        return mocktopus.getHandler();
    }

    /*@Provides
    @Singleton
    GithubService provideGithubService() {

    }*/


}

