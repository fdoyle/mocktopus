package com.lacronicus.mocktopus.mocktopusdriver.fakeservice;

import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyCollectionContainingModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModdedModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by fdoyle on 7/10/14.
 */
public interface FakeService {
//    public MyModel returnMyModel();
    public Observable<MyCollectionContainingModel> returnMyCollectionContainingModel();
//    public MyCollectionExtendingModel returnMyCollectionExtendingModel(); //todo fix this one
//    public List<MyModel> returnMyModelList();
//    public List<List<MyModel>> returnMyModelListList();
//    public ArrayList<MyModel> returnMyModelArrayList();
//    public Observable<MyModel> returnMyModelObservable();
//    public Observable<List<MyModdedModel>> getCustomCollection();
    public Observable<List<String>> returnStringList(); // todo this is "supported" but there's no way to annotate it, since there are no fields defined for it
}
