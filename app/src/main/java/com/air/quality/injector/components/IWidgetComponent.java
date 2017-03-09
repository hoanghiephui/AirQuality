package com.air.quality.injector.components;

import com.air.quality.injector.modules.WidgetModule;
import com.air.quality.injector.scope.PerActivity;
import com.air.quality.ui.widget.AirQualityWidgetReceiver;

import dagger.Component;

/**
 * Created by hoanghiep on 3/2/17.
 */

@PerActivity
@Component(modules = WidgetModule.class)
public interface IWidgetComponent {
  void inject(AirQualityWidgetReceiver airQualityWidgetReceiver);
}
