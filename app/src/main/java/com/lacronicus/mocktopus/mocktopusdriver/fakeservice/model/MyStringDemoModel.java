package com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.string.StringDate;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.string.StringFixed;

/**
 * Created by fdoyle on 7/16/14.
 */
public class MyStringDemoModel {
    @StringFixed("A fixed string")
    public String myFixedString;
    @StringDate
    public String myDefaultDateString;
    @StringDate("MM/dd/yyyy")
    public String myCustomDateString;
}
