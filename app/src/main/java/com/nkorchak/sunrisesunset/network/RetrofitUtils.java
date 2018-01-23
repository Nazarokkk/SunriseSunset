package com.nkorchak.sunrisesunset.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class RetrofitUtils {

    private static final String TAG = RetrofitUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.sunrise-sunset.org";
    private static OkHttpClient.Builder okHttpClient = null;

    public static RetrofitApi getApiServiceRx() {

        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'")
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(createHttpClient().build())
                .build()
                .create(RetrofitApi.class);
    }

    private static OkHttpClient.Builder createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .addInterceptor(logging);
        }

        return okHttpClient;
    }
}

