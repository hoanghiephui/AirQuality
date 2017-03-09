package com.air.quality.api;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.api.models.MapAirQualityData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hoanghiep on 2/28/17.
 */

public interface IAirQualityServices {
  @GET("feed/{city}/")
  Observable<AirQualityData> getFeedCity(@Path("city") String city, @Query("token") String token);

  @GET("/map/bounds/")
  Observable<MapAirQualityData> getMapsAirQuality(@Query("latlng") String latlng, @Query("token") String token);
}
