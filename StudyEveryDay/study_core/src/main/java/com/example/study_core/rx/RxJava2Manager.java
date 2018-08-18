package com.example.study_core.rx;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Manager {
    private RxBus rxBus;

    private RxJava2Manager() {
        rxBus = new RxBus();
    }

    public static RxJava2Manager getRxJava2ManagerInstance() {
        return RxJava2ManagerHolder.RxHolder;
    }

    private static class RxJava2ManagerHolder {
        private static final RxJava2Manager RxHolder = new RxJava2Manager();
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void add(Disposable observer) {
        disposable.add(observer);
    }

    public void clear() {
        disposable.clear();
    }

    public void sendEvent(Object event) {
        rxBus.send(event);
    }

    public void ConsumeEvent(Consumer<Object> consumer) {
        disposable.add(rxBus.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }


}
