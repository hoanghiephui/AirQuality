package com.air.quality.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.air.quality.ui.activitys.MainActivity;

/**
 * Created by hoanghiep on 3/2/17.
 */

public class AirQualityUtils {
  public static boolean isMarshmallow() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  public static boolean isLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  public static boolean isJellyBeanMR2() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
  }

  public static boolean isJellyBeanMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
  }

  public static boolean isLocationPermissionGranted(Context context) {
    int coarseLocationPermissionResult = ContextCompat.checkSelfPermission(
      context,
      android.Manifest.permission.ACCESS_COARSE_LOCATION);

    int fineLocationPermissionResult = ContextCompat.checkSelfPermission(
      context,
      android.Manifest.permission.ACCESS_FINE_LOCATION);

    return coarseLocationPermissionResult == PackageManager.PERMISSION_GRANTED
      && fineLocationPermissionResult == PackageManager.PERMISSION_GRANTED;
  }

  public static PendingIntent startActivityWithPendingIntent(@NonNull Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return PendingIntent.getActivity(context, 0, intent, 0);
  }

  public static int valueQuality(int value) {
    if (value <= 50) {
      return 0;
    } else if (51 <= value && value <= 100) {
      return 1;
    } else if (101 <= value && value <= 150) {
      return 2;
    } else if (151 <= value && value <= 200) {
      return 3;
    } else if (201 <= value && value <= 300) {
      return 4;
    } else {
      return 5;
    }
  }
}
