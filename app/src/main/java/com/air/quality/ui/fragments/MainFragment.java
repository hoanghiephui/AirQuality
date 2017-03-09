package com.air.quality.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.air.quality.AirQualityApplication;
import com.air.quality.R;
import com.air.quality.RxBus;
import com.air.quality.api.models.AirQualityData;
import com.air.quality.events.EventBus;
import com.air.quality.injector.components.DaggerIFeedCityComponent;
import com.air.quality.injector.components.IApplicationComponent;
import com.air.quality.injector.components.IFeedCityComponent;
import com.air.quality.injector.modules.FeedCityModule;
import com.air.quality.mvp.contracts.IFeedCityContract;
import com.air.quality.ui.widget.ShortTimeView;
import com.github.ahmadnemati.wind.WindView;
import com.github.ahmadnemati.wind.enums.TrendType;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.air.quality.IConstants.TOKEN;
import static com.air.quality.utils.AirQualityUtils.valueQuality;
import static com.air.quality.utils.DateUtils.dateTimeToTimestamp;

/**
 * Created by hoanghiep on 3/5/17.
 */

public class MainFragment extends BaseFragment implements IFeedCityContract.View{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.fab)
  FloatingActionButton fab;
  @BindView(R.id.tvDesAir)
  TextView tvDesAri;
  @BindView(R.id.tvTemp)
  TextView tvTemp;
  @BindView(R.id.air_icon)
  AppCompatImageView airIcon;
  @BindView(R.id.temperature)
  TextView temperature;
  @BindView(R.id.weather_icon)
  ImageView weatherIcon;
  @BindView(R.id.description)
  TextView desWeather;
  @BindView(R.id.last_update)
  ShortTimeView lastUpdate;
  @BindView(R.id.bgAirHome)
  ImageView bgAirHome;
  @BindView(R.id.windview)
  WindView windView;

  @Inject
  IFeedCityContract.Presenter mPresenter;

  @Override
  protected void injectDependences() {
    IApplicationComponent applicationComponent = ((AirQualityApplication)getActivity(). getApplication()).getApplicationComponent();
    IFeedCityComponent feedCityComponent = DaggerIFeedCityComponent.builder()
      .iApplicationComponent(applicationComponent)
      .feedCityModule(new FeedCityModule())
      .build();
    feedCityComponent.inject(this);
  }

  @Override
  protected void attachView() {
    mPresenter.attachView(this);
  }

  @Override
  protected int getFragmentLayout() {
    return R.layout.fragment_main;
  }

  @Override
  protected void bindEventHandlers(View view) {
    subscribeMetaChangedEvent();
    mPresenter.getFeedCity("hanoi", TOKEN);
  }

  private void setAirQualityHome(int value) {
    Activity activity = getActivity();
    switch (value) {
      case 0:
        airIcon.setImageResource(R.drawable.ic_air_good);
        bgAirHome.setImageResource(R.drawable.good);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_good));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_good));
        tvDesAri.setText(getText(R.string.air_good));
        break;
      case 1:
        airIcon.setImageResource(R.drawable.ic_air_moderate);
        bgAirHome.setImageResource(R.drawable.moderate);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_moderate));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_moderate));
        tvDesAri.setText(getText(R.string.air_moderate));
        break;
      case 2:
        airIcon.setImageResource(R.drawable.ic_air_sensitive);
        bgAirHome.setImageResource(R.drawable.moderate);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_sensitive));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_sensitive));
        tvDesAri.setText(getText(R.string.air_unhealthy_sensitive));
        break;
      case 3:
        airIcon.setImageResource(R.drawable.ic_air_unhealthy);
        bgAirHome.setImageResource(R.drawable.unhealthy);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_unhealthy));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_unhealthy));
        tvDesAri.setText(getText(R.string.air_unhealthy));
        break;
      case 4:
        airIcon.setImageResource(R.drawable.ic_air_very_unhealthy);
        bgAirHome.setImageResource(R.drawable.very_unhealthy);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_very_unhealthy));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_very_unhealthy));
        tvDesAri.setText(getText(R.string.air_very_unhealthy));
        break;
      case 5:
        airIcon.setImageResource(R.drawable.ic_air_hazardous);
        bgAirHome.setImageResource(R.drawable.hazardous);
        tvDesAri.setTextColor(ContextCompat.getColor(activity, R.color.color_air_hazardous));
        temperature.setTextColor(ContextCompat.getColor(activity, R.color.color_air_hazardous));
        tvDesAri.setText(getText(R.string.air_hazardous));
        break;
      default:
        break;
    }
  }

  @Override
  public void showFeedCity(AirQualityData airQualityData) {
    AirQualityData.Data data = airQualityData.getData();
    int aqi = data.getAqi();
    setAirQualityHome(valueQuality(aqi));
    temperature.setText("AQI: " + String.valueOf(aqi));
    lastUpdate.setTime(dateTimeToTimestamp(data.getTime().getS()));
    tvTemp.setText(String.valueOf(Math.round(data.getIaqi().getTemp().getValue())) + " ℃");
    windView.animateBaroMeter();
    windView.setPressure((float) Math.round(data.getIaqi().getPressure().getValue()));
    windView.setPressureUnit("in Hg");
    windView.setWindSpeed(3);
    windView.setWindSpeedUnit(" km/h");
    windView.setTrendType(TrendType.UP);
    windView.start();
    toolbar.setTitle(data.getCity().getName());
  }

  @Override
  public void showProgress() {

  }

  @Override
  public void hideProgress() {

  }

  private void subscribeMetaChangedEvent() {
    Subscription subscription = RxBus.getInstance()
      .toObservable(EventBus.Locations.class)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .distinctUntilChanged()
      .subscribe(new Action1<EventBus.Locations>() {
        @Override
        public void call(EventBus.Locations event) {

        }
      }, new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {

        }
      });
    RxBus.getInstance().addSubscription(this, subscription);
  }
}
