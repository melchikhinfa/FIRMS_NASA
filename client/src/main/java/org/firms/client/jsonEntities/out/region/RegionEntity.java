package org.firms.client.jsonEntities.out.region;

import java.util.UUID;

public class RegionEntity {

    private UUID id;
    private String shortName;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String fullName) {
        this.name = fullName;
    }

    public RegionEntity() {
    }

    public RegionEntity(UUID id, String shortName, String fullName) {
        this.id = id;
        this.shortName = shortName;
        this.name = fullName;
    }
}
