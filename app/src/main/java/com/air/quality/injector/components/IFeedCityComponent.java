package com.air.quality.injector.components;

import com.air.quality.services.AirQualityService;
import com.air.quality.ui.activitys.MainActivity;
import com.air.quality.injector.modules.FeedCityModule;
import com.air.quality.injector.scope.PerActivity;
import com.air.quality.ui.fragments.MainFragment;
import com.air.quality.ui.fragments.MapsAirFragment;

import dagger.Component;

/**
 * Created by hoanghiep on 2/28/17.
 */

@PerActivity
@Component(dependencies = IApplicationComponent.class, modules = FeedCityModule.class)
public interface IFeedCityComponent {
  void inject(MainActivity mainActivity);
  void inject(AirQualityService airQualityService);
  void inject(MainFragment mainFragment);
}
