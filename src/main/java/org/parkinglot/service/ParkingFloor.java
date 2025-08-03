package org.parkinglot.service;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.SpotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ParkingFloor {

    private final int floorNumber;
    private boolean isFloorUnderMaintenance;
    private final Map<SpotType, Map<String,ParkingSpot>> parkingSpots; //Map<SpotType,Map<SpotId,ParkingSpot>>
    private static final Logger logger = LoggerFactory.getLogger(ParkingFloor.class);

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        isFloorUnderMaintenance = false;
        parkingSpots = new HashMap<>();
    }

    public ParkingSpot addNewParkingSpot(SpotType type){
        Map<String,ParkingSpot> spots;
        if(parkingSpots.containsKey(type)){
            spots = parkingSpots.get(type);
        }
        else{
            spots = new HashMap<>();
            parkingSpots.put(type,spots);
        }
        ParkingSpot spot = new ParkingSpot(UUID.randomUUID().toString(),type);
        spots.put(spot.getSpotId(),spot);
        logger.info("New parking spot has been created with Id [{}] and with Size [{}]",spot.getSpotId(),spot.getSpotType());
        return spot;
    }

    public ParkingSpot removeParkingSpot(SpotType type, String spotId){
        Map<String,ParkingSpot> spots = parkingSpots.get(type);
        if(spots == null)
            return null;
        ParkingSpot spot = spots.get(spotId);
        if(spot != null){

            if(!spot.isAvailable()){
                logger.error("Parking spot is occupied at the moment . Spot Id [{}]",spot.getSpotId());
                return null;
            }
            spots.remove(spotId);
            logger.info("Parking spot has been removed from Parking lot with Id [{}] and with Size [{}]",spot.getSpotId(),spot.getSpotType());
            return spot;
        }
        return null;
    }

    public ParkingSpot getRandomFreeSpot(SpotType type){
        if(isFloorUnderMaintenance) return null;
        Map<String,ParkingSpot> spotMap = parkingSpots.get(type);
        if(spotMap == null) return null;

        for(Map.Entry<String,ParkingSpot> entry : spotMap.entrySet() ){
            if(entry.getValue().isAvailable())
                return entry.getValue();
        }
        return null;
    }


    public boolean isFloorUnderMaintenance() {
        return isFloorUnderMaintenance;
    }

    public void setFloorActive() {
        isFloorUnderMaintenance = false;
        logger.info("Parking floor [{}] is set to active",floorNumber);
    }

    public void setFloorUnderMaintenance(){

        if(!isFloorEmpty()){
            logger.error("Can't set floor to maintenance. Floor is not empty");
            return ;
        }
        logger.info("Parking floor [{}] is set to under maintenance", floorNumber);
        isFloorUnderMaintenance = true;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean isFloorEmpty(){
        for(Map<String,ParkingSpot> spotMap : parkingSpots.values()){
            for(ParkingSpot p : spotMap.values()){
                if(!p.isAvailable()){
                    return false;
                }
            }
        }
        return true;
    }
}
