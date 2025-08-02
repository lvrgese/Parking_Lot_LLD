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

    public void occupy(Vehicle vehicle){
        this.vehicle = vehicle;
        isAvailable = false;
    }

    public void release(){
        this.vehicle = null;
        isAvailable = true;
    }
}
