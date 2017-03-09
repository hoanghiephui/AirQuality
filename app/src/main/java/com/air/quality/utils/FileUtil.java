package com.air.quality.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class FileUtil {
  private static final String HTTP_CACHE_DIR = "http";

  public static File getHttpCacheDir(Context context) {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return new File(context.getExternalCacheDir(), HTTP_CACHE_DIR);
    }
    return new File(context.getCacheDir(), HTTP_CACHE_DIR);
  }
}
