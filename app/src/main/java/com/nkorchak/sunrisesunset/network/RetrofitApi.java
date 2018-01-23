package com.nkorchak.sunrisesunset.network;

import com.nkorchak.sunrisesunset.models.SunRiseSunSetResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public interface RetrofitApi {

    @GET("/json?date=today")
    Observable<SunRiseSunSetResponse> getSunRiseSunSet(@Query("lat") double lat,
                                                       @Query("lng") double lon);

}
