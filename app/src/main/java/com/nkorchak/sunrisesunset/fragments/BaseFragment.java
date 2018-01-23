package com.nkorchak.sunrisesunset.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nkorchak.sunrisesunset.interfaces.IBasePresenter;
import com.nkorchak.sunrisesunset.views.BaseView;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class BaseFragment<T extends IBasePresenter> extends Fragment implements BaseView {

    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (presenter != null) this.presenter.onAttach();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) this.presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) this.presenter.onResume();
    }

    @Override
    public void onPause() {
        if (presenter != null) this.presenter.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        if (presenter != null) this.presenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            this.presenter.onDestroy();
            this.presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public Context context() {
        return ((BaseView) getActivity()).context();
    }

    @Override
    public void showLoading() {
        ((BaseView) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            ((BaseView) getActivity()).hideLoading();
        }
    }

    @Override
    public void showSnackBar(String message) {
        ((BaseView) getActivity()).showSnackBar(message);
    }
}