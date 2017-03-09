package com.air.quality.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hoanghiep on 2/28/17.
 */

public class AirQualityData extends BaseResponse implements Serializable {
  @Expose
  @SerializedName("data")
  private Data data;

  public Data getData() {
    return data;
  }

  public static class Data {
    @Expose
    @SerializedName("aqi")
    private int aqi;
    @Expose
    @SerializedName("idx")
    private int idx;
    @Expose
    @SerializedName("attributions")
    private List<Attribution> attributions;
    @Expose
    @SerializedName("city")
    private City city;
    @Expose
    @SerializedName("dominentpol")
    private String dominentpol;
    @Expose
    @SerializedName("iaqi")
    private Iaqi iaqi;
    @Expose
    @SerializedName("time")
    private Time time;

    public int getAqi() {
      return aqi;
    }

    public int getIdx() {
      return idx;
    }

    public List<Attribution> getAttributions() {
      return attributions;
    }

    public City getCity() {
      return city;
    }

    public String getDominentpol() {
      return dominentpol;
    }

    public Iaqi getIaqi() {
      return iaqi;
    }

    public Time getTime() {
      return time;
    }
  }

  public static class Time {
    @Expose
    @SerializedName("s")
    private String s;
    @Expose
    @SerializedName("tz")
    private String tz;
    @Expose
    @SerializedName("v")
    private long v;

    public String getS() {
      return s;
    }

    public String getTz() {
      return tz;
    }

    public long getV() {
      return v;
    }
  }

  public static class Iaqi {
    @Expose
    @SerializedName("h")
    private Humidity humidity;
    @Expose
    @SerializedName("no2")
    private No2 no2;
    @Expose
    @SerializedName("o3")
    private O3 o3;
    @Expose
    @SerializedName("p")
    private Pressure pressure;
    @Expose
    @SerializedName("pm25")
    private Pm25 pm25;
    @Expose
    @SerializedName("so2")
    private So2 so2;
    @Expose
    @SerializedName("t")
    private Temp temp;

    public Humidity getHumidity() {
      return humidity;
    }

    public No2 getNo2() {
      return no2;
    }

    public O3 getO3() {
      return o3;
    }

    public Pressure getPressure() {
      return pressure;
    }

    public Pm25 getPm25() {
      return pm25;
    }

    public So2 getSo2() {
      return so2;
    }

    public Temp getTemp() {
      return temp;
    }

    public static class Humidity extends BaseValue {
    }

    public static class No2 extends BaseValue {
    }

    public static class O3 extends BaseValue {
    }

    public static class Pressure extends BaseValue {
    }

    public static class Pm25 extends BaseValue {
    }

    public static class So2 extends BaseValue {
    }

    public static class Temp extends BaseValue {
    }

    public static class BaseValue {
      @Expose
      @SerializedName("v")
      private double value;

      public double getValue() {
        return value;
      }
    }
  }

  public static class City {
    @Expose
    @SerializedName("geo")
    private List<Double> geo;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("url")
    private String url;

    public List<Double> getGeo() {
      return geo;
    }

    public String getName() {
      return name;
    }

    public String getUrl() {
      return url;
    }
  }

  public static class Attribution {
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("name")
    private String name;

    public String getUrl() {
      return url;
    }

    public String getName() {
      return name;
    }
  }
}
