package com.nkorchak.sunrisesunset.useCases;

import com.nkorchak.sunrisesunset.network.RetrofitUtils;

import rx.Observable;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class GetSunRiseSunSetUseCase extends BaseUseCase {

    private Double lat;
    private Double lon;

    public GetSunRiseSunSetUseCase() {
    }

    public void setData(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    protected Observable getObservable() {
        return RetrofitUtils.getApiServiceRx().getSunRiseSunSet(lat, lon);
    }
}
