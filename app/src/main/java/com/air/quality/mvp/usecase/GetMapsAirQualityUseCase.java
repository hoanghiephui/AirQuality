package com.air.quality.mvp.usecase;

import com.air.quality.api.models.MapAirQualityData;
import com.air.quality.respository.IRepository;

import rx.Observable;

/**
 * Created by hoanghiep on 3/5/17.
 */

public class GetMapsAirQualityUseCase extends UseCase<GetMapsAirQualityUseCase.RequestValues, GetMapsAirQualityUseCase.ResponseValue> {
  private IRepository repository;

  public GetMapsAirQualityUseCase(IRepository repository) {
    this.repository = repository;
  }

  @Override
  public ResponseValue execute(RequestValues requestValues) {
    return new ResponseValue(repository.getMapsAirQuality(requestValues.getLatlng(), requestValues.getToken()));
  }

  public static final class RequestValues implements UseCase.RequestValues {
    private String latlng;
    private String token;

    public RequestValues(String latlng, String token) {
      this.latlng = latlng;
      this.token = token;
    }

    public String getLatlng() {
      return latlng;
    }

    public String getToken() {
      return token;
    }
  }

  public static final class ResponseValue implements UseCase.ResponseValue {
    private final Observable<MapAirQualityData> mapsAirQuality;

    public ResponseValue(Observable<MapAirQualityData> mapsAirQuality) {
      this.mapsAirQuality = mapsAirQuality;
    }

    public Observable<MapAirQualityData> getMapsAirQuality() {
      return mapsAirQuality;
    }
  }
}
