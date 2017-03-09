package com.air.quality.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by hoanghiep on 3/4/17.
 */

public class DateUtils {
  public static Long getFormattedDate(Date created) {
    long time;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss", Locale.getDefault());
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date date = dateFormat.parse(dateToString(created));
      dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      Timestamp tm = Timestamp.valueOf(dateFormat.format(date));
      time = tm.getTime();
      return time;
    } catch (ParseException ignored) {
      return new Date().getTime();
    }
  }

  public static String dateToString(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss", Locale.getDefault());
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = null;
    try {
      dateString = sdf.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateString;
  }

  public static Long dateTimeToTimestamp(String date) {
    long time;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date dates = dateFormat.parse(date);
      Timestamp tm = Timestamp.valueOf(dateFormat.format(dates));
      time = tm.getTime();
      return time;
    } catch (ParseException ignored) {
      return new Date().getTime();
    }
  }

  public static Long dateTimeToTimestamp(String date, String template) {
    long time;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(template, Locale.getDefault());
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date dates = dateFormat.parse(date);
      Timestamp tm = Timestamp.valueOf(dateFormat.format(dates));
      time = tm.getTime();
      return time;
    } catch (ParseException ignored) {
      ignored.printStackTrace();
    }
    return null;
  }
}
