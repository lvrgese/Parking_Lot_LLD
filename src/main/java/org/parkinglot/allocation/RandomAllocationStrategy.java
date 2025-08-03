package org.parkinglot.allocation;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.SpotType;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.entity.VehicleType;
import org.parkinglot.factory.ParkingTicketFactory;
import org.parkinglot.service.ParkingFloor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RandomAllocationStrategy implements AllocationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RandomAllocationStrategy.class);
    @Override
    public ParkingSpot getParkingSpot(List<ParkingFloor> parkingFloors, Vehicle vehicle) {
        ParkingSpot spot= null;
        SpotType type = getSpotType(vehicle.vehicleType());
        for( ParkingFloor floor : parkingFloors){
            spot = floor.getRandomFreeSpot(type);
            if(spot != null) {
                logger.info("Parking spot allocated for vehicle [{}] at spot [{}] ",vehicle.registrationNumber(),spot.getSpotId());
                return spot;
            }
        }
        logger.warn("Couldn't find a free spot for vehicle [{}] ",vehicle.registrationNumber());
        return null;
    }

    private SpotType getSpotType(VehicleType type){
        switch (type){
            case TWO_WHEELER -> {
                return SpotType.S;
            }
            case COMPACT_CAR -> {
                return SpotType.M;
            }
            case LARGE_CAR -> {
                return SpotType.L;
            }
            case HEAVY_VEHICLE -> {
                return SpotType.XL;
            }
        }

        return null;
    }
}
