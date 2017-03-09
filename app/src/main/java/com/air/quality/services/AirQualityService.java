package com.air.quality.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.air.quality.AirQualityApplication;
import com.air.quality.api.models.AirQualityData;
import com.air.quality.injector.components.DaggerIFeedCityComponent;
import com.air.quality.injector.components.IApplicationComponent;
import com.air.quality.injector.components.IFeedCityComponent;
import com.air.quality.injector.modules.FeedCityModule;
import com.air.quality.mvp.contracts.IFeedCityContract;

import javax.inject.Inject;

import static com.air.quality.IConstants.TOKEN;

/**
 * Created by hoanghiep on 3/2/17.
 */

public class AirQualityService extends Service implements IFeedCityContract.View {
  @Inject
  IFeedCityContract.Presenter mPresenter;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    injectDependences();
    attachView();
    mPresenter.getFeedCity("hanoi", TOKEN);
  }

  protected void injectDependences() {
    IApplicationComponent applicationComponent = ((AirQualityApplication) getApplication()).getApplicationComponent();
    IFeedCityComponent feedCityComponent = DaggerIFeedCityComponent.builder()
      .iApplicationComponent(applicationComponent)
      .feedCityModule(new FeedCityModule())
      .build();
    feedCityComponent.inject(this);
  }

  protected void attachView() {
    mPresenter.attachView(this);
  }

  @Override
  public void showFeedCity(AirQualityData airQualityData) {

  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void showProgress() {

  }

  @Override
  public void hideProgress() {

  }
}
