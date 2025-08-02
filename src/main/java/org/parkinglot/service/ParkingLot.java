package org.parkinglot.service;

import org.parkinglot.allocation.AllocationStrategy;
import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.event.ParkingTicketGeneratedEvent;
import org.parkinglot.event.VehicleExitEvent;
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
        return  floor;
    }

    public boolean setParkingFloorUnderMaintenance(int floorNumber, boolean value){
        ParkingFloor item = null;
        for(ParkingFloor f : parkingFloors){
            if(f.getFloorNumber() == floorNumber){
                item =f;
                break;
            }
        }
        if(item == null) {
            System.err.println("Invalid floor number");
            return false;
        }
        item.setFloorUnderMaintenance(value);
        return true;
    }


    @EventListener
    public void addParkingTicket(ParkingTicketGeneratedEvent event){
        activeTickets.put(event.getTicket().ticketId(),event.getTicket());
    }

    @EventListener
    public ParkingTicket removeActiveTicket(VehicleExitEvent event){
        return activeTickets.remove(event.getTicketId());
    }
}
