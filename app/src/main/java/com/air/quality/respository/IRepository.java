package com.air.quality.respository;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.api.models.MapAirQualityData;

import rx.Observable;

/**
 * Created by hoanghiep on 2/28/17.
 */
public interface IRepository {
  Observable<AirQualityData> getFeedCity(String city, String token);
  Observable<MapAirQualityData> getMapsAirQuality(String latlng, String token);
}
