package org.firms.client.jsonEntities.out.subscription;

import java.util.UUID;

public class SubscriptionEntity {

  private UUID id;
  private String shortName;
  private String regionName;
  private double latitude;
  private double longitude;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(UUID id, String shortName, String regionName, double latitude, double longitude) {
        this.id = id;
        this.shortName = shortName;
        this.regionName = regionName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
