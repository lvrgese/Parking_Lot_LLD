package org.parkinglot.service;

import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.payment.PaymentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CostCalculator {
    @Value("${per.hour.small}")
    private int costPerHourForSmall;
    @Value("${per.hour.medium}")
    private int costPerHourForMedium;
    @Value("${per.hour.large}")
    private int costPerHourForLarge;
    @Value("${per.hour.x_large}")
    private int costPerHourForXLarge;
    private static final Logger logger = LoggerFactory.getLogger(CostCalculator.class);


    public int calculateFee(ParkingTicket ticket) {
        LocalDateTime entryTime = ticket.parkedTime();
        LocalDateTime exitTime = LocalDateTime.now();

        long hours = Duration.between(entryTime,exitTime).toHours();
        if(hours == 0) hours =1;

        int rate = switch (ticket.vehicle().vehicleType()){
            case TWO_WHEELER -> costPerHourForSmall;
            case COMPACT_CAR -> costPerHourForMedium;
            case LARGE_CAR -> costPerHourForLarge;
            case HEAVY_VEHICLE -> costPerHourForXLarge;
        };

        int total=  (int) hours*rate;
        logger.info("Cost calculated as [{}] INR for Vehicle [{}] , TicketID - [{}]",total,ticket.vehicle(),ticket.ticketId());
        return total;
    }
}
