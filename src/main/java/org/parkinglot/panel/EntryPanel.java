package org.parkinglot.panel;
import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.entity.VehicleType;
import org.parkinglot.event.ParkingTicketGeneratedEvent;
import org.parkinglot.factory.ParkingTicketFactory;
import org.parkinglot.service.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EntryPanel {

    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingLot parkingLot;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EntryPanel(ParkingTicketFactory parkingTicketFactory, ParkingLot parkingLot, ApplicationEventPublisher applicationEventPublisher){
        this.parkingTicketFactory = parkingTicketFactory;
        this.parkingLot = parkingLot;
        this.applicationEventPublisher = applicationEventPublisher;
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
        spot.occupy(vehicle);
        ParkingTicket ticket=  parkingTicketFactory.createTicket(vehicle,spot);
        applicationEventPublisher.publishEvent(new ParkingTicketGeneratedEvent(this,ticket));
        return ticket;
    }
}
