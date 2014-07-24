package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by fdoyle on 7/24/14.
 */
public class ConfigFragmentAdapter extends FragmentPagerAdapter {

    Mocktopus mocktopus;
    ArrayList<Type> services;

    public ConfigFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setContent(Mocktopus mocktopus) {
        this.mocktopus = mocktopus;
        services = new ArrayList<Type>(mocktopus.getApiSet());

    }

    @Override
    public ConfigurationFragment getItem(int position) {
        ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setContent(mocktopus.getHandler(services.get(position)));
        return fragment;
    }

    @Override
    public int getCount() {
        if(mocktopus == null) {
            return 0;
        } else {
            return mocktopus.getApiCount();
        }
    }
}
