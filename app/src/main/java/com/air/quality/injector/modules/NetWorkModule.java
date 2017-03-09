package com.air.quality.injector.modules;

import android.util.Log;

import com.air.quality.AirQualityApplication;
import com.air.quality.BuildConfig;
import com.air.quality.IConstants;
import com.air.quality.injector.scope.PerApplication;
import com.air.quality.respository.IRepository;
import com.air.quality.respository.RepositoryImpl;
import com.air.quality.utils.FileUtil;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hoanghiep on 2/28/17.
 */

@Module
public class NetWorkModule {
  private AirQualityApplication airQualityApplication;

  public NetWorkModule(AirQualityApplication airQualityApplication) {
    this.airQualityApplication = airQualityApplication;
  }

  @Provides
  @PerApplication
  IRepository provideRepository(@Named("ariQuality") Retrofit ariQuality) {
    return new RepositoryImpl(airQualityApplication, ariQuality);
  }

  @Provides
  @Named("ariQuality")
  @PerApplication
  Retrofit provideAriQualityApi() {
    String endpoint = IConstants.BASE_API_URL_ARI_QUALITY;
    GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().create());

    OkHttpClient httpClient = new OkHttpClient.Builder()
      .cache(new Cache(FileUtil.getHttpCacheDir(airQualityApplication.getContext()), IConstants.HTTP_CACHE_SIZE))
      .connectTimeout(IConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(IConstants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
      .addInterceptor(new LoggingInterceptor.Builder()
        .loggable(BuildConfig.DEBUG)
        .setLevel(Level.BASIC)
        .log(Log.INFO)
        .request("Request")
        .response("Response")
        .addHeader("version", BuildConfig.VERSION_NAME)
        .build())
      .build();
    return new Retrofit.Builder()
      .baseUrl(endpoint)
      .addConverterFactory(gsonConverterFactory)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .client(httpClient)
      .build();
  }
}
