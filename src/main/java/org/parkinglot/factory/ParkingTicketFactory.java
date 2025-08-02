package org.parkinglot.factory;

import org.parkinglot.entity.ParkingSpot;
import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ParkingTicketFactory {
    public ParkingTicket createTicket(Vehicle vehicle, ParkingSpot spot) {

        String ticketId = UUID.randomUUID().toString();
        LocalDateTime entryTime = LocalDateTime.now();

        return new ParkingTicket(ticketId,entryTime,spot,vehicle );
    }
}
