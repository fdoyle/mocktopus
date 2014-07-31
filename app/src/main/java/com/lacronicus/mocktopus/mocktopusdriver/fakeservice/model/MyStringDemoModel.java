package com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model;

import com.lacronicus.mocktopus.core.mocktopus.annotation.string.StringDate;
import com.lacronicus.mocktopus.core.mocktopus.annotation.string.StringFixed;

/**
 * Created by fdoyle on 7/16/14.
 */
public class MyStringDemoModel {
    @StringFixed("Something Fixed")
    public String myFixedString;
    @StringDate
    public String myDefaultDateString;
    @StringDate("MM/dd/yyyy")
    public String myCustomDateString;
}
