package com.lacronicus.mocktopus.mocktopusdriver.mocktopus.modder;

import java.util.List;

/**
 * Created by fdoyle on 7/24/14.
 *
 * this interface is used in the ListBuilder annotation
 *
 * When a list is being filled with objects, this interface
 * gives the process some intelligence that mocktopus otherwise couldnt offer
 *
 * by default, a list is filled with multiple copies of the same item, based on the current config
 *
 * this "builder" allows you to modify the list after it has been created, creating some variety in the list items
 */
public interface IListModder<T> {

    //defines how long the
    public int getCount();

    public void modifyList(List<T> list);
}
