package com.air.quality.mvp.contracts;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.mvp.presenters.BasePresenter;
import com.air.quality.mvp.view.BaseView;

/**
 * Created by hoanghiep on 2/28/17.
 */

public interface IFeedCityContract {
  interface View extends BaseView {
    void showFeedCity(AirQualityData airQualityData);
  }

  interface Presenter extends BasePresenter<View> {
    void getFeedCity(String city, String token);
  }
}
