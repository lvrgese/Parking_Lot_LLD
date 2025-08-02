package org.parkinglot.panel;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.entity.VehicleType;
import org.parkinglot.event.VehicleExitEvent;
import org.parkinglot.factory.ParkingTicketFactory;
import org.parkinglot.service.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EntryPanel {

    private final Map<String,ParkingTicket> activeTickets;
    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingLot parkingLot;

    @Autowired
    public EntryPanel(ParkingTicketFactory parkingTicketFactory, ParkingLot parkingLot){
        this.parkingTicketFactory = parkingTicketFactory;
        this.parkingLot = parkingLot;
        activeTickets = new HashMap<>();
    }

    public ParkingTicket processVehicleEntry(String registrationNumber, VehicleType type){
        Vehicle vehicle = new Vehicle(registrationNumber,type);
        ParkingSpot spot =parkingLot.getParkingSpot(vehicle);
        if(spot == null){
            System.err.println("Slot is not available at the moment");
            return null;
        }
        if(!spot.isAvailable()){
            throw new RuntimeException("Spot is already occupied");
        }
        spot.setVehicle(vehicle);
        ParkingTicket ticket=  parkingTicketFactory.createTicket(vehicle,spot);
        activeTickets.put(ticket.ticketId(),ticket);
        return ticket;
    }

    @EventListener
    public ParkingTicket removeActiveTicket(VehicleExitEvent event){
        return activeTickets.remove(event.getTicketId());
    }
}
