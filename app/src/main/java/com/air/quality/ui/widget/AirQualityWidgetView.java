package com.air.quality.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.air.quality.R;

import static com.air.quality.utils.AirQualityUtils.startActivityWithPendingIntent;

/**
 * Created by hoanghiep on 3/2/17.
 */

public class AirQualityWidgetView {
  public void refreshCurrentLocationAirQuality(Context context, Intent intent) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    ComponentName componentName = new ComponentName(context, AirQualityWidgetReceiver.class);
    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_air_quality);
  }

  public void initSetting(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
    final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_air_quality);
    remoteViews.setOnClickPendingIntent(R.id.widget, startActivityWithPendingIntent(context));
  }
}
