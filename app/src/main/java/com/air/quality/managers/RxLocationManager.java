package com.air.quality.managers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import ru.solodovnikov.rxlocationmanager.LocationRequestBuilder;
import ru.solodovnikov.rxlocationmanager.LocationTime;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hoanghiep on 3/2/17.
 */

public class RxLocationManager {
  private LocationRequestBuilder locationManager;
  private Location mLocation;

  public RxLocationManager(Context context) {
    this.locationManager = new LocationRequestBuilder(context);
  }

  public Location requestLocationUpdate() {
    locationManager.addLastLocation(LocationManager.NETWORK_PROVIDER, new LocationTime(30, TimeUnit.SECONDS), false)
      .addRequestLocation(LocationManager.GPS_PROVIDER, new LocationTime(10, TimeUnit.SECONDS))
      .setDefaultLocation(new Location(LocationManager.PASSIVE_PROVIDER))
      .create().subscribe(new Subscriber<Location>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        Log.d(getClass().getSimpleName(), e.toString());
      }

      @Override
      public void onNext(Location location) {
        mLocation = location;
        final String name = Thread.currentThread().getName();
        Log.d(getClass().getSimpleName(), location != null ? location.toString() : "Location is empty =(");
      }
    });
    return mLocation;
  }
}
