package org.parkinglot.entity;

import java.time.LocalDateTime;

public record ParkingTicket(String ticketId, LocalDateTime parkedTime,
                            ParkingSpot parkingSpot, Vehicle vehicle)
{ }
