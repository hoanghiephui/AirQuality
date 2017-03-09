package com.air.quality.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.air.quality.AirQualityApplication;
import com.air.quality.injector.components.DaggerIWidgetComponent;
import com.air.quality.injector.components.IApplicationComponent;
import com.air.quality.injector.components.IWidgetComponent;
import com.air.quality.injector.modules.WidgetModule;
import com.air.quality.services.AirQualityService;

import javax.inject.Inject;

import static com.air.quality.utils.AirQualityUtils.isLocationPermissionGranted;

/**
 * Created by hoanghiep on 3/2/17.
   */

public class AirQualityWidgetReceiver extends AppWidgetProvider {
  @Inject
  AirQualityWidgetView airQualityWidgetView;

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    if (!isLocationPermissionGranted(context)) {
      return;
    }
    context.startService(new Intent(context, AirQualityService.class));
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    IWidgetComponent widgetComponent = DaggerIWidgetComponent.builder()
      .widgetModule(new WidgetModule()).build();
    widgetComponent.inject(this);
    airQualityWidgetView.initSetting(context, appWidgetManager, appWidgetIds);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
  }
}
