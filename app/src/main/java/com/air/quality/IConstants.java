package com.air.quality;

/**
 * Created by hoanghiep on 2/28/17.
 */

public interface IConstants {
  String BASE_API_URL_ARI_QUALITY = "https://api.waqi.info/";
  String TOKEN = "ec50e67e8f1379551a371440f95108b4e93a58ae";
  int HTTP_CACHE_SIZE = 20 * 1024 * 1024;
  int HTTP_CONNECT_TIMEOUT = 15 * 1000;
  int HTTP_READ_TIMEOUT = 20 * 1000;
}
