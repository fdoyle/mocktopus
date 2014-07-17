package com.lacronicus.mocktopus.mocktopusdriver.service;

import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyCollectionContainingModel;
import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyCollectionExtendingModel;
import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/10/14.
 */
public interface ApiService {
    public MyModel returnMyModel();
    public MyCollectionContainingModel returnMyCollectionContainingModel();
    //public MyCollectionExtendingModel returnMyCollectionExtendingModel(); //todo fix this one
    public List<MyModel> returnMyModelList();
    public ArrayList<MyModel> returnMyModelArrayList();
}
