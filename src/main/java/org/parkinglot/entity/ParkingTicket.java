package org.parkinglot.entity;

import java.time.LocalDateTime;

public class ParkingTicket {
    private final String ticketId;
    private final LocalDateTime parkedTime;
    private final ParkingSpot parkingSpot;
    private final Vehicle vehicle;
    private boolean isActive;

    public ParkingTicket(String ticketId, LocalDateTime parkedTime, ParkingSpot parkingSpot, Vehicle vehicle) {
        this.ticketId = ticketId;
        this.parkedTime = parkedTime;
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.isActive = true;
    }

    public String ticketId() {
        return ticketId;
    }

    public LocalDateTime parkedTime() {
        return parkedTime;
    }

    public ParkingSpot parkingSpot() {
        return parkingSpot;
    }

    public Vehicle vehicle() {
        return vehicle;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setInactive() {
        isActive = false;
    }
}



