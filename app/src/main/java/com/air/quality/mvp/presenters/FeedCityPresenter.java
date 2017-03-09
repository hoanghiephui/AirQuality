package com.air.quality.mvp.presenters;

import android.util.Log;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.mvp.contracts.IFeedCityContract;
import com.air.quality.mvp.usecase.GetFeedCityUseCase;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class FeedCityPresenter implements IFeedCityContract.Presenter {
  private static final String TAG = FeedCityPresenter.class.getSimpleName();
  private GetFeedCityUseCase feedCityUseCase;
  private IFeedCityContract.View view;
  private CompositeSubscription compositeSubscription;

  public FeedCityPresenter(GetFeedCityUseCase feedCityUseCase) {
    this.feedCityUseCase = feedCityUseCase;
  }

  @Override
  public void getFeedCity(String city, String token) {
    view.showProgress();
    compositeSubscription.clear();
    Subscription subscription = feedCityUseCase.execute(new GetFeedCityUseCase.RequestValues(city, token))
      .getFeedCity()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends AirQualityData>>() {
        @Override
        public Observable<? extends AirQualityData> call(Throwable throwable) {
          return Observable.empty();
        }
      })
      .subscribe(new Action1<AirQualityData>() {
        @Override
        public void call(AirQualityData feedCity) {
          view.hideProgress();
          view.showFeedCity(feedCity);
        }
      });
    compositeSubscription.add(subscription);
  }

  @Override
  public void attachView(IFeedCityContract.View view) {
    this.view = view;
    compositeSubscription = new CompositeSubscription();
  }

  @Override
  public void subscribe() {

  }

  @Override
  public void unsubscribe() {
    compositeSubscription.clear();
  }
}
