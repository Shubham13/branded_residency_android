package com.paragon.sensonic.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class AsyncCommand<T> {


    public ObservableEmitter<T> mSubscriber;

    public ObservableEmitter<T> getSubscriber() {
        return mSubscriber;
    }

    public abstract T run() throws Exception;

    public Observable<T> returnObservable() {
        return Observable.create(
                new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                        mSubscriber = subscriber;
                        try {
                            subscriber.onNext(run());
                            subscriber.onComplete();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
