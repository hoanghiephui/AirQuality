package com.air.quality;

/**
 * Created by hoanghiep on 3/1/17.
 */

public enum AirQualityIndex {
  GOOD(0),
  MODERATE(1),
  UNHEALTHY_SENSITIVE(2),
  UNHEALTHY(3),
  VERY_UNHEALTHY(4),
  HAZARDOUS(5);

  private int value;

  AirQualityIndex(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
