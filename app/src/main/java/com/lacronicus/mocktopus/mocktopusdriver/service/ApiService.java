package com.lacronicus.mocktopus.mocktopusdriver.service;

import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyCollectionModel;
import com.lacronicus.mocktopus.mocktopusdriver.service.model.MyModel;

import java.util.List;

/**
 * Created by fdoyle on 7/10/14.
 */
public interface ApiService {
    public MyModel returnMyModel();
    public MyModel returnMyModel2();
    public MyModel returnMyModel3();
    public MyCollectionModel returnMyModelList();
}
