package org.firms.client.jsonEntities.out.subscription;


public class FireEntity {
    private double latitude;
    private double longitude;

    private String annotation;

    @Override
    public String toString() {
        return "\uD83D\uDD25 Обнаружен пожар в радиусе 1км!\n \uD83D\uDCCD Координаты точки:" + latitude + ", " + longitude;
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

    public FireEntity() {
    }

    public FireEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
