package com.nkorchak.sunrisesunset.presenters;

import com.nkorchak.sunrisesunset.views.BaseView;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    protected void setView(V view) {
        this.view = view;
    }

    public void onAttach() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
        view = null;
    }
}
