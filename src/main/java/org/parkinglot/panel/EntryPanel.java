package org.parkinglot.panel;
import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.parkinglot.entity.VehicleType;
import org.parkinglot.event.ParkingTicketGeneratedEvent;
import org.parkinglot.factory.ParkingTicketFactory;
import org.parkinglot.service.ParkingLot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EntryPanel {

    private final ParkingTicketFactory parkingTicketFactory;
    private final ParkingLot parkingLot;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Logger logger = LoggerFactory.getLogger(EntryPanel.class);

    @Autowired
    public EntryPanel(ParkingTicketFactory parkingTicketFactory, ParkingLot parkingLot, ApplicationEventPublisher applicationEventPublisher){
        this.parkingTicketFactory = parkingTicketFactory;
        this.parkingLot = parkingLot;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ParkingTicket parkNewVehicle(String registrationNumber, VehicleType type){

        logger.info("Entry requested for vehicle [{}]", registrationNumber);
        Vehicle vehicle = new Vehicle(registrationNumber,type);
        ParkingSpot spot =parkingLot.getParkingSpot(vehicle);
        if(spot == null){
            logger.error("Slot is not available at the moment");
            return null;
        }
        if(!spot.isAvailable()){
            logger.error("Spot is already occupied");
            return null;
        }
        spot.occupy(vehicle);
        ParkingTicket ticket=  parkingTicketFactory.createTicket(vehicle,spot);
        logger.info("Ticket [{}] successfully created for vehicle [{}]", ticket.ticketId(), vehicle.registrationNumber());
        applicationEventPublisher.publishEvent(new ParkingTicketGeneratedEvent(this,ticket));
        return ticket;

    }
}
