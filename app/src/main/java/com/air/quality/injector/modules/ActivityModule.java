package com.air.quality.injector.modules;

import android.app.Activity;
import android.content.Context;

import com.air.quality.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 2/28/17.
 */

@Module
public class ActivityModule {
  private final Activity mActivity;

  public ActivityModule(Activity mActivity) {
    this.mActivity = mActivity;
  }

  @Provides
  @PerActivity
  public Context provideContext() {
    return mActivity;
  }
}
