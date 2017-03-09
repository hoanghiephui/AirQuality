package com.air.quality.injector.components;

import com.air.quality.injector.modules.MapsAirQualityModule;
import com.air.quality.injector.scope.PerActivity;
import com.air.quality.ui.fragments.MapsAirFragment;

import dagger.Component;

/**
 * Created by hoanghiep on 3/5/17.
 */

@PerActivity
@Component(dependencies = IApplicationComponent.class, modules = MapsAirQualityModule.class)
public interface IMapsAirComponent {

  void inject(MapsAirFragment mapsAirFragment);
}
