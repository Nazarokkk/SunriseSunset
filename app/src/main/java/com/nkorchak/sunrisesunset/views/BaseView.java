package com.nkorchak.sunrisesunset.views;

import android.content.Context;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public interface BaseView {

    Context context();

    void showLoading();

    void hideLoading();

    void showSnackBar(String message);
}