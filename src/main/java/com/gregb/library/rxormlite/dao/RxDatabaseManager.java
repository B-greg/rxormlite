package com.gregb.library.rxormlite.dao;

import android.util.Log;
import java.util.List;
import java.util.concurrent.Callable;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by gregoire barret on 6/4/15.
 * For Tops project.
 */
public abstract class RxDatabaseManager<T extends DatabaseModel> extends DatabaseManager<T>
        implements IRxRepository<T>{
    public static final String TAG = "RxDatabaseManager";


    protected static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch(Exception ex) {
                            Log.e(TAG, "Error reading from the database", ex);
                        }
                    }
                });
    }
    @Override
    public Observable<List<T>> RxGetAll() {
        return makeObservable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return GetAll();
            }
        });
    }

    @Override
    public Observable<T> RxGetById(final int id) {
        return makeObservable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return GetById(id);
            }
        });
    }

    @Override
    public Observable RxAdd(final T entite) {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Add(entite);
                return null;
            }
        });
    }

    @Override
    public Observable RxUpdate(final T entite) {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Update(entite);
                return null;
            }
        });
    }

    @Override
    public Observable RxDelete(final T entite) {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Delete(entite);
                return null;
            }
        });
    }

    @Override
    public Observable RxDeleteAll() {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                DeleteAll();
                return null;
            }
        });
    }

    @Override
    public Observable RxRefresh(final T entite) {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Refresh(entite);
                return null;
            }
        });
    }

    @Override
    public Observable RxCreateOrUpdate(final T entite) {
        return makeObservable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                CreateOrUpdate(entite);
                return null;
            }
        });
    }
}
