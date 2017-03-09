package com.air.quality.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoanghiep on 3/5/17.
 */

public class MapAirQualityData extends BaseResponse {
  @Expose
  @SerializedName("data")
  private List<Maps> data;

  public List<Maps> getData() {
    return data;
  }

  public static class Maps {
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lon")
    private double lon;
    @Expose
    @SerializedName("uid")
    private int uid;
    @Expose
    @SerializedName("aqi")
    private String aqi;

    public double getLat() {
      return lat;
    }

    public double getLon() {
      return lon;
    }

    public int getUid() {
      return uid;
    }

    public String getAqi() {
      return aqi;
    }
  }
}
