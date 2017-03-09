package com.air.quality.mvp.presenters;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.api.models.MapAirQualityData;
import com.air.quality.mvp.contracts.IMapsAirQualityContract;
import com.air.quality.mvp.usecase.GetMapsAirQualityUseCase;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.schedulers.ScheduledAction;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hoanghiep on 3/5/17.
 */

public class MapsAirQualityPresenter implements IMapsAirQualityContract.Presenter {
  private GetMapsAirQualityUseCase mapsAirQualityUseCase;
  private IMapsAirQualityContract.View view;
  private CompositeSubscription compositeSubscription;

  public MapsAirQualityPresenter(GetMapsAirQualityUseCase mapsAirQualityUseCase) {
    this.mapsAirQualityUseCase = mapsAirQualityUseCase;
  }

  @Override
  public void attachView(IMapsAirQualityContract.View view) {
    compositeSubscription = new CompositeSubscription();
    this.view = view;
  }

  @Override
  public void subscribe() {

  }

  @Override
  public void unsubscribe() {
    compositeSubscription.unsubscribe();
  }

  @Override
  public void getMapsAirQuality(String latlng, String token) {
    compositeSubscription.clear();
    view.showProgress();
    Subscription subscription = mapsAirQualityUseCase.execute(new GetMapsAirQualityUseCase.RequestValues(latlng, token))
      .getMapsAirQuality()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends MapAirQualityData>>() {
        @Override
        public Observable<? extends MapAirQualityData> call(Throwable throwable) {
          return Observable.empty();
        }
      })
      .subscribe(new Action1<MapAirQualityData>() {
        @Override
        public void call(MapAirQualityData mapAirQualityData) {
          if (mapAirQualityData.getStatus().equals("ok")) {
            view.showListMaps(mapAirQualityData);
          }
          view.hideProgress();
        }
      });
    compositeSubscription.add(subscription);
  }
}
