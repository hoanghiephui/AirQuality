package com.air.quality.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class BaseResponse {
  private static final String STATUS = "status";

  @Expose
  @SerializedName(STATUS)
  private String status;

  public String getStatus() {
    return status;
  }
}
