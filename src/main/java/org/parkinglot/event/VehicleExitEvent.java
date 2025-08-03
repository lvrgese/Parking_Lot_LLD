package org.parkinglot.event;

import org.springframework.context.ApplicationEvent;

public class VehicleExitEvent extends ApplicationEvent {
    private final String ticketId;

    public VehicleExitEvent(Object source,String ticketId) {
        super(source);
        this.ticketId= ticketId;
    }

    public String getTicketId() {
        return ticketId;
    }
}
