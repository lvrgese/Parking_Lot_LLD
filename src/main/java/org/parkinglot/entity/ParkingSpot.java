package org.parkinglot.entity;

import org.parkinglot.service.CostCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkingSpot {

    private final String spotId;
    private final SpotType spotType;
    private boolean isAvailable;
    private Vehicle vehicle;
    private static final Logger logger = LoggerFactory.getLogger(ParkingSpot.class);
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
        logger.info("Vehicle parked in spot [{}]",spotId);
    }

    public void release(){
        this.vehicle = null;
        isAvailable = true;
        logger.info("Vehicle exited from spot [{}]",spotId);
    }
}
