package com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.collection.ListBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.builder.IListBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/14/14.
 */
public class MyModel {
    public MyStringDemoModel child;
    public String myString;
    public String myString2;
    public Integer myInt;
    public Double myDouble;
    public Character myChar;
    public Boolean myBool;
    //todo what if this contains another MyModel?


}
