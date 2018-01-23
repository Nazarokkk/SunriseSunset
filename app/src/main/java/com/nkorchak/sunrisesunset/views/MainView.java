package com.nkorchak.sunrisesunset.views;

import com.nkorchak.sunrisesunset.models.SunRiseSunSetResponse;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public interface MainView extends BaseView {
    void updateUi(SunRiseSunSetResponse sunRiseSunSetResponse);
}
