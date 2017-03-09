package layout;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.air.quality.R;
import com.air.quality.services.AirQualityService;

import static com.air.quality.utils.AirQualityUtils.isLocationPermissionGranted;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AirQualityWidgetConfigureActivity AirQualityWidgetConfigureActivity}
 */
public class AirQualityWidget extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {

    CharSequence widgetText = AirQualityWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.air_quality_widget);
    views.setTextViewText(R.id.appwidget_text, widgetText);
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    // When the user deletes the widget, delete the preference associated with it.
    for (int appWidgetId : appWidgetIds) {
      AirQualityWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
    if (!isLocationPermissionGranted(context)) {
      return;
    }
    context.startService(new Intent(context, AirQualityService.class));
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

