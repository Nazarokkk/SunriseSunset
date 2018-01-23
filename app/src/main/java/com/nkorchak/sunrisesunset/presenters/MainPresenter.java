package com.nkorchak.sunrisesunset.presenters;

import com.nkorchak.sunrisesunset.interfaces.IMainPresenter;
import com.nkorchak.sunrisesunset.models.SunRiseSunSetResponse;
import com.nkorchak.sunrisesunset.useCases.GetSunRiseSunSetUseCase;
import com.nkorchak.sunrisesunset.views.MainView;

import rx.Subscriber;

/**
 * Created by nazarkorchak on 23.01.18.
 */

public class MainPresenter extends BasePresenter<MainView> implements IMainPresenter {
    private GetSunRiseSunSetUseCase getSunRiseSunSetUseCase;

    public MainPresenter(MainView view) {
        setView(view);
        getSunRiseSunSetUseCase = new GetSunRiseSunSetUseCase();
    }

    @Override
    public void getSunRiseSunSet(double lat, double lon) {
        getSunRiseSunSetUseCase.setData(lat, lon);
        getSunRiseSunSetUseCase.execute(new Subscriber<SunRiseSunSetResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SunRiseSunSetResponse sunRiseSunSetResponse) {
                if (sunRiseSunSetResponse.getStatus().equals("OK")) {
                    view.updateUi(sunRiseSunSetResponse);
                }
            }
        });
    }
}
