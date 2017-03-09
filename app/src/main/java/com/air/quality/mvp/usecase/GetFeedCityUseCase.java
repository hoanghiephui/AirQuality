package com.air.quality.mvp.usecase;

import com.air.quality.api.models.AirQualityData;
import com.air.quality.respository.IRepository;

import rx.Observable;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class GetFeedCityUseCase extends UseCase<GetFeedCityUseCase.RequestValues, GetFeedCityUseCase.ResponseValue> {
  private IRepository repository;

  public GetFeedCityUseCase(IRepository repository) {
    this.repository = repository;
  }

  @Override
  public ResponseValue execute(RequestValues requestValues) {
    return new ResponseValue(repository.getFeedCity(requestValues.getCity(), requestValues.getToken()));
  }

  public static final class RequestValues implements UseCase.RequestValues {
    private String city;
    private String token;

    public RequestValues(String city, String token) {
      this.city = city;
      this.token = token;
    }

    public String getCity() {
      return city;
    }

    public String getToken() {
      return token;
    }
  }

  public static final class ResponseValue implements UseCase.ResponseValue {
    private final Observable<AirQualityData> feedCity;

    public ResponseValue(Observable<AirQualityData> feedCity) {
      this.feedCity = feedCity;
    }

    public Observable<AirQualityData> getFeedCity() {
      return feedCity;
    }
  }
}
