package org.parkinglot.factory;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ParkingTicketFactory {
    private static final Logger logger = LoggerFactory.getLogger(ParkingTicketFactory.class);
    public ParkingTicket createTicket(Vehicle vehicle, ParkingSpot spot) {

        String ticketId = UUID.randomUUID().toString();
        LocalDateTime entryTime = LocalDateTime.now();

        ParkingTicket ticket = new ParkingTicket(ticketId,entryTime,spot,vehicle);
        logger.info("Parking ticket generated for vehicle [{}] with Id [{}] . Parking spot assigned : [{}]",vehicle.registrationNumber(),
                ticket.ticketId(),ticket.parkingSpot().getSpotId());
        return ticket;
    }
}
