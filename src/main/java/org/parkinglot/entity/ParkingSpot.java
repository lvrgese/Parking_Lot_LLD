package org.parkinglot.entity;

public class ParkingSpot {

    private final String spotId;
    private final SpotType spotType;
    private boolean isAvailable;
    private Vehicle vehicle;

    public ParkingSpot(String spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
        isAvailable = true;
        vehicle = null;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
        vehicle = null;
    }
}
