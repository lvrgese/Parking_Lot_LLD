package org.parkinglot.event;

import org.parkinglot.entity.ParkingTicket;
import org.springframework.context.ApplicationEvent;

public class ParkingTicketGeneratedEvent extends ApplicationEvent {

    private final ParkingTicket ticket;
    public ParkingTicketGeneratedEvent(Object source, ParkingTicket ticket) {
        super(source);
        this.ticket = ticket;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }
}
