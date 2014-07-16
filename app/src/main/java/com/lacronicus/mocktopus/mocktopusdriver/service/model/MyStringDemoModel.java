package com.lacronicus.mocktopus.mocktopusdriver.service.model;

import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringDate;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.annotation.StringFixed;

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
