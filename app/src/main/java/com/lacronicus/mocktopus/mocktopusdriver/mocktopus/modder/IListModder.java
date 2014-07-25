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
 * for example, a list of "news article" models might have three different states: plaintext, text + image, text+video
 * by default, mocktopus will create duplicate objects. You can configure them, but you would have to configure all of them at once
 *
 * this pattern allows you to be a bit smarter
 *
 * In the above example, you might want one of each "class" of model (plaintext, text+image, text+video) represented in your list.
 *
 * To do this, return 3 from getCount(). This tells the object inflater to add three items to your list.
 *
 * Then, this list is passed into modifyList. A list of duplicate items is passed in, and you can modify them to your needs,
 * restructuring the first item so that it's a plainText article, the second so that it's text + image, etc.
 */
public interface IListModder<T> {

    /**
     * tell the list inflater how many items it should add
    */
    public int getCount();

    public void modifyList(List<T> list);
}
