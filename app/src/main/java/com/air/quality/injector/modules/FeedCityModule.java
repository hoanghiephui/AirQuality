package com.air.quality.injector.modules;

import com.air.quality.mvp.contracts.IFeedCityContract;
import com.air.quality.mvp.presenters.FeedCityPresenter;
import com.air.quality.mvp.usecase.GetFeedCityUseCase;
import com.air.quality.respository.IRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 2/28/17.
 */

@Module
public class FeedCityModule {
  @Provides
  GetFeedCityUseCase getFeedCityUseCase(IRepository repository) {
    return new GetFeedCityUseCase(repository);
  }

  @Provides
  IFeedCityContract.Presenter getFeedCity(GetFeedCityUseCase getFeedCityUseCase) {
    return new FeedCityPresenter(getFeedCityUseCase);
  }
}
