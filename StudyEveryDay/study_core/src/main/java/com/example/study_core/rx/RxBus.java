package com.example.study_core.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    public RxBus() {
    }


    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object action) {

        bus.onNext(action);
    }


    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
