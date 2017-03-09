package com.air.quality.respository;

import android.content.Context;

import com.air.quality.api.IAirQualityServices;
import com.air.quality.api.models.AirQualityData;
import com.air.quality.api.models.MapAirQualityData;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by hoanghiep on 2/28/17.
 */
public class RepositoryImpl implements IRepository {
  private IAirQualityServices mAirQualityServices;
  private Context mContext;

  public RepositoryImpl(Context context, Retrofit ariQuality) {
    mContext = context;
    mAirQualityServices = ariQuality.create(IAirQualityServices.class);
  }

  //api get feed city
  @Override
  public Observable<AirQualityData> getFeedCity(String city, String token) {
    return mAirQualityServices.getFeedCity(city, token);
  }

  @Override
  public Observable<MapAirQualityData> getMapsAirQuality(String latlng, String token) {
    return mAirQualityServices.getMapsAirQuality(latlng, token);
  }
}
