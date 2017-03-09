package com.air.quality;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.air.quality.injector.components.DaggerIApplicationComponent;
import com.air.quality.injector.components.IApplicationComponent;
import com.air.quality.injector.modules.ApplicationModule;
import com.air.quality.injector.modules.NetWorkModule;
import com.air.quality.managers.PermissionManager;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class AirQualityApplication extends Application {
  private static final String TAG = AirQualityApplication.class.getSimpleName();
  private Context mContext;
  private IApplicationComponent mApplicationComponent;
  private LatLng latLng;

  static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  /**
   * Called when the application is starting, before any activity, service,
   * or receiver objects (excluding content providers) have been created.
   * Implementations should be as quick as possible (for example using
   * lazy initialization of state) since the time spent in this function
   * directly impacts the performance of starting the first activity,
   * service, or receiver in a process.
   * If you override this method, be sure to call super.onCreate().
   */
  @Override
  public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
    setupInjector();
    PermissionManager.init(this);
  }

  private void setupInjector() {
    mApplicationComponent = DaggerIApplicationComponent.builder()
      .applicationModule(new ApplicationModule(this))
      .netWorkModule(new NetWorkModule(this)).build();
  }

  public Context getContext() {
    return mContext;
  }

  public IApplicationComponent getApplicationComponent() {
    return mApplicationComponent;
  }

  public LatLng getLatLng() {
    return latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }
}
