package com.air.quality.injector.modules;

import android.app.Application;

import com.air.quality.AirQualityApplication;
import com.air.quality.injector.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 2/28/17.
 */

@Module
public class ApplicationModule {
  private final AirQualityApplication application;

  public ApplicationModule(AirQualityApplication application) {
    this.application = application;
  }

  @Provides
  @PerApplication
  public AirQualityApplication provideAriQualityApp() {
    return application;
  }

  @Provides
  @PerApplication
  public Application provideApplication() {
    return application;
  }
}
