package org.parkinglot.allocation;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.service.ParkingFloor;

import java.util.List;

public interface AllocationStrategy {

    ParkingSpot getParkingSpot(List<ParkingFloor> parkingFloors, Vehicle vehicle);
}
