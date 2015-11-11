package com.gregb.library.rxormlite.dao;

import java.util.List;
import rx.Observable;

/**
 * Created by gregoire barret on 6/4/15.
 * For Tops project.
 */
public interface IRxRepository<T> {

    Observable<List<T>> RxGetAll();
    Observable<T> RxGetById(int id);

    Observable RxAdd(T entite);
    Observable RxUpdate(T entite);
    Observable RxDelete(T entite);
    Observable RxDeleteAll();
    Observable RxRefresh(T entite);
    Observable RxCreateOrUpdate(T entite);
}
