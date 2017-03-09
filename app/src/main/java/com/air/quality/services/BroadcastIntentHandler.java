package com.air.quality.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.air.quality.api.models.AirQualityData;

/**
 * Created by hoanghiep on 3/3/17.
 */

public class BroadcastIntentHandler {
  public static final String ACTION_WIDGET_UPDATE = "action_widget_update";
  public static final String ACTION_ACTIVITY_UPDATE = "action_activity_update";
  public static final String ACTION_QUERY_BRIEF = "action_query_brief";
  public static final String ACTION_QUERY_DETAIL = "action_query_detail";
  public static final String ACTION_REQUEST_PERMISSION = "action_request_permission";
  public static final String LOCATION_PERMISSION_NEEDED = "location_permission_needed";

  private BroadcastIntentHandler() {
  }

  public static boolean isWeatherUpdateRequestByWidget(Intent intent) {
    return intent.getAction() != null && intent.getAction().equals(ACTION_QUERY_BRIEF);
  }

  public static boolean isWeatherUpdateRequestByActivity(Intent intent) {
    return intent.getAction() != null && intent.getAction().equals(ACTION_QUERY_DETAIL);
  }

  public static boolean isWeatherUpdateForWidget(Intent intent) {
    return intent.getAction() != null && intent.getAction().equals(ACTION_WIDGET_UPDATE);
  }

  public static boolean isWeatherUpdateForActivity(Intent intent) {
    return intent.getAction() != null && intent.getAction().equals(ACTION_ACTIVITY_UPDATE);
  }

  public static void broadcastBriefWeatherUpdateForWidget(Context context, AirQualityData currently) {
    Intent intent = new Intent(BroadcastIntentHandler.ACTION_WIDGET_UPDATE);

    /*intent.putExtra(WEATHER_ICON_SERIAL_NUMBER, currently.getCurrently().getIcon());
    intent.putExtra(TEMPERATURE, currently.getCurrently().getTemperature());
    intent.putExtra(WEATHER_DESCRIPTION, currently.getCurrently().getSummary());
    intent.putExtra(AIR_QUALITY, currently.getAirQualityData());*/

    context.sendBroadcast(intent);
  }



  public static void broadcastLocationPermissionNeededForCaller(Context context) {
    Intent intent = new Intent(LOCATION_PERMISSION_NEEDED);
    context.sendBroadcast(intent);
  }

  public static void broadcastBriefWeatherUpdateRequest(Context context) {
    context.sendBroadcast(new Intent(ACTION_QUERY_BRIEF));
  }

  public static void broadcastDetailWeatherUpdateRequest(Context context) {
    context.sendBroadcast(new Intent(ACTION_QUERY_DETAIL));
  }
}
