package org.parkinglot.service;

import org.parkinglot.allocation.AllocationStrategy;
import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLot {

    private final List<ParkingFloor> parkingFloors;
    private final AllocationStrategy allocationStrategy;

    @Autowired
    public ParkingLot(AllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
        parkingFloors = new ArrayList<>();
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle){
        return allocationStrategy.getParkingSpot(parkingFloors, vehicle);
    }
}
