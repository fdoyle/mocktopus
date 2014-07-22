package com.lacronicus.mocktopus.mocktopusdriver.fakeservice;

import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyCollectionContainingModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by fdoyle on 7/10/14.
 */
public interface ApiService {
    @GET("/foo")
    public MyModel returnMyModel();
    public MyCollectionContainingModel returnMyCollectionContainingModel();
    //public MyCollectionExtendingModel returnMyCollectionExtendingModel(); //todo fix this one
    public List<MyModel> returnMyModelList();
    public List<List<MyModel>> returnMyModelListList();
    public ArrayList<MyModel> returnMyModelArrayList();
    public Observable<MyModel> returnMyModelObservable();
}
