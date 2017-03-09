package com.air.quality.permission;

/**
 * Created by hoanghiep on 3/2/17.
 */

public interface PermissionCallback {
  void permissionGranted();

  void permissionRefused();
}
