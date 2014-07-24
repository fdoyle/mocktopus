package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.builder;

import java.util.List;

/**
 * Created by fdoyle on 7/24/14.
 */
public interface IListBuilder<T> {
    public int getCount();

    public void modifyList(List<T> list);
}
