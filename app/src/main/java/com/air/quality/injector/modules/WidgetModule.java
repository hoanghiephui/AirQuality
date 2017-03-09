package com.air.quality.injector.modules;

import com.air.quality.ui.widget.AirQualityWidgetView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 3/2/17.
 */

@Module
public class WidgetModule {
  @Provides
  public AirQualityWidgetView providesAirQualityWidgetView() {
    return new AirQualityWidgetView();
  }
}
