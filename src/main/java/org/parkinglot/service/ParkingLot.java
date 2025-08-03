package org.parkinglot.service;

import org.parkinglot.allocation.AllocationStrategy;
import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.event.ParkingTicketGeneratedEvent;
import org.parkinglot.event.VehicleExitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingLot {

    private final List<ParkingFloor> parkingFloors;
    private final AllocationStrategy allocationStrategy;
    private final Map<String, ParkingTicket> activeTickets;
    private int currentFloor;

    private static final Logger logger = LoggerFactory.getLogger(ParkingLot.class);

    @Autowired
    public ParkingLot(AllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
        parkingFloors = new ArrayList<>();
        activeTickets = new HashMap<>();
        currentFloor =0;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle){
        return allocationStrategy.getParkingSpot(parkingFloors, vehicle);
    }

    public ParkingFloor addParkingFloor(){

        ParkingFloor floor = new ParkingFloor(currentFloor++);
        parkingFloors.add(floor);

        logger.info("New Parking floor is added with Id : {}",floor.getFloorNumber());
        return  floor;
    }


    @EventListener
    public void addParkingTicket(ParkingTicketGeneratedEvent event){
        activeTickets.put(event.getTicket().ticketId(),event.getTicket());
        logger.info("Parking ticket with Id [{}] has been added to Parking Lot",event.getTicket().ticketId());
    }

    @EventListener
    public ParkingTicket removeActiveTicket(VehicleExitEvent event){

        ParkingTicket t = activeTickets.remove(event.getTicketId());
        logger.info("Parking ticket with Id [{}] has been removed from Parking Lot",t.ticketId());
        return t;
    }
}
