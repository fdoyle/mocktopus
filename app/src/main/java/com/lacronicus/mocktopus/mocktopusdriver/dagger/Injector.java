package com.lacronicus.mocktopus.mocktopusdriver.dagger;

import android.content.Context;

/**
 * Created by fdoyle on 7/17/14.
 */
public class Injector {
    public static void inject(Object toInject, Context context) {
        ((IObjectGraph) context.getApplicationContext()).getObjectGraph().inject(toInject);
    }

    public static void inject(Context context) {
        ((IObjectGraph) context.getApplicationContext()).getObjectGraph().inject(context);
    }

    private Context mContext;

    public Injector(Context context) {
        mContext = context;
    }

    public void inject(Object toInject) {
        ((IObjectGraph) mContext.getApplicationContext()).getObjectGraph().inject(toInject);
    }
}