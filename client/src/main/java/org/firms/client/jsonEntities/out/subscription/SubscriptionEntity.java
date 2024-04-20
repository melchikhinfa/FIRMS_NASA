package org.firms.client.jsonEntities.out.subscription;

import java.util.UUID;

public class SubscriptionEntity {

  private UUID id;
  private String shortName;
  private String regionName;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(UUID id, String shortName, String regionName) {
        this.id = id;
        this.shortName = shortName;
        this.regionName = regionName;
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
}
