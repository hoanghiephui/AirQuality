package com.air.quality.mvp.contracts;

import com.air.quality.api.models.MapAirQualityData;
import com.air.quality.mvp.presenters.BasePresenter;
import com.air.quality.mvp.view.BaseView;

/**
 * Created by hoanghiep on 3/5/17.
 */

public interface IMapsAirQualityContract {
  interface View extends BaseView {
    void showListMaps(MapAirQualityData mapAirQualityData);
  }

  interface Presenter extends BasePresenter<View> {
    void getMapsAirQuality(String latlng, String token);
  }
}
