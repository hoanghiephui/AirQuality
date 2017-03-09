package com.air.quality.events;

/**
 * Created by hoanghiep on 3/6/17.
 */

public class EventBus {
  public static class Locations {
    private final double latitude;
    private final double longitude;

    public Locations(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
    }

    public double getLatitude() {
      return latitude;
    }

    public double getLongitude() {
      return longitude;
    }
  }
}
