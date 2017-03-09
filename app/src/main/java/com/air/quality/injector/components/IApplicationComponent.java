package com.air.quality.injector.components;

import android.app.Application;

import com.air.quality.AirQualityApplication;
import com.air.quality.injector.modules.ApplicationModule;
import com.air.quality.injector.modules.NetWorkModule;
import com.air.quality.injector.scope.PerApplication;
import com.air.quality.respository.IRepository;

import dagger.Component;

/**
 * Created by hoanghiep on 2/28/17.
 */
@PerApplication
@Component(modules = {ApplicationModule.class, NetWorkModule.class})
public interface IApplicationComponent {
  Application application();

  AirQualityApplication ariQualityApp();

  IRepository repository();
}
