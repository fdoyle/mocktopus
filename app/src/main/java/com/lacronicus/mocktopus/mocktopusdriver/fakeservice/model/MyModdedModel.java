package com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model;

import com.lacronicus.mocktopus.core.mocktopus.annotation.collection.ListModder;
import com.lacronicus.mocktopus.core.mocktopus.modder.IListModder;

import java.util.List;

/**
 * Created by fdoyle on 7/24/14.
 */
@ListModder(MyModdedModel.MyModelListModder.class)
public class MyModdedModel {
    public String myString1;
    public String myString2;
    public String myString3;
    public String position;

    public static class MyModelListModder implements IListModder<MyModdedModel> {
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public void modifyList(List<MyModdedModel> list) {
            for(int i = 0; i != list.size(); i++) {
                list.get(i).position = "position " + String.valueOf(i);
            }
        }
    }
}
