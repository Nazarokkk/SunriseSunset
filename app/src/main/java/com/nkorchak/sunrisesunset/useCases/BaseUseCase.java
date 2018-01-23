package com.nkorchak.sunrisesunset.useCases;

import android.support.annotation.Nullable;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public abstract class BaseUseCase {

    private Subscription mSubscription;
    private Scheduler mWorkerScheduler;
    private Scheduler mPostScheduler;

    protected abstract Observable getObservable();

    public BaseUseCase() {
        mWorkerScheduler = Schedulers.io();
        mPostScheduler = AndroidSchedulers.mainThread();
    }

    public BaseUseCase(Scheduler workerScheduler, Scheduler postScheduler) {
        mWorkerScheduler = workerScheduler;
        mPostScheduler = postScheduler;
    }

    public void execute() {
        this.execute(null);
    }

    public void execute(@Nullable Subscriber subscriber) {
        Observable observable = getObservable().subscribeOn(mWorkerScheduler)
                .observeOn(mPostScheduler);
        if (subscriber != null) {
            mSubscription = observable.subscribe(subscriber);
        } else {
            mSubscription = observable.subscribe();
        }
    }

    public void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
    }
}
