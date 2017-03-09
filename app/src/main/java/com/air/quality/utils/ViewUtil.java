package com.air.quality.utils;

import android.content.Context;
import android.text.format.DateFormat;

/**
 * Created by hoanghiep on 3/4/17.
 */

public class ViewUtil {
  public static String formatSameDayTime(final Context context, final long timestamp) {
    if (context == null) return null;
    if (android.text.format.DateUtils.isToday(timestamp))
      return android.text.format.DateUtils.formatDateTime(context, timestamp,
        DateFormat.is24HourFormat(context) ? android.text.format.DateUtils.FORMAT_SHOW_TIME | android.text.format.DateUtils.FORMAT_24HOUR
          : android.text.format.DateUtils.FORMAT_SHOW_TIME | android.text.format.DateUtils.FORMAT_12HOUR);
    return android.text.format.DateUtils.formatDateTime(context, timestamp, android.text.format.DateUtils.FORMAT_SHOW_DATE);
  }
}
