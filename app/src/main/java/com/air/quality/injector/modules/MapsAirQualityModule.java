package com.air.quality.injector.modules;

import com.air.quality.mvp.contracts.IMapsAirQualityContract;
import com.air.quality.mvp.presenters.MapsAirQualityPresenter;
import com.air.quality.mvp.usecase.GetMapsAirQualityUseCase;
import com.air.quality.respository.IRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 3/5/17.
 */

@Module
public class MapsAirQualityModule {
  @Provides
  GetMapsAirQualityUseCase prvidesMapsAirQualityUseCase(IRepository repository) {
    return new GetMapsAirQualityUseCase(repository);
  }

  @Provides
  IMapsAirQualityContract.Presenter providesMapsAirQualityContract(GetMapsAirQualityUseCase mapsAirQualityUseCase) {
    return new MapsAirQualityPresenter(mapsAirQualityUseCase);
  }
}
