package com.nkorchak.sunrisesunset.interfaces;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public interface IBasePresenter {
    void onAttach();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
