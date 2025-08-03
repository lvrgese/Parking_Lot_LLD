package org.parkinglot.panel;

import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Receipt;
import org.parkinglot.event.VehicleExitEvent;
import org.parkinglot.payment.PaymentProcessor;
import org.parkinglot.payment.PaymentType;
import org.parkinglot.service.CostCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExitPanel {

    private final CostCalculator costCalculator;
    private final PaymentProcessor paymentProcessor;
    private static final Logger logger = LoggerFactory.getLogger(ExitPanel.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ExitPanel(CostCalculator costCalculator, PaymentProcessor paymentProcessor, ApplicationEventPublisher applicationEventPublisher) {
        this.costCalculator = costCalculator;
        this.paymentProcessor = paymentProcessor;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Receipt releaseVehicle(ParkingTicket ticket, PaymentType paymentType){
        if(ticket == null || !ticket.isActive()) {
            logger.error("Invalid Ticket");
            return null;
        }
        logger.info("Exit requested for ticket [{}]", ticket.ticketId());

        int fee = costCalculator.calculateFee(ticket);
        logger.info("Payment process started for ticket [{}]", ticket.ticketId());
        if (!paymentProcessor.processPayment(fee,paymentType)){
            System.err.println("Payment failed,try again");
            return null;
        }
        ticket.parkingSpot().release();
        ticket.setInactive();
        applicationEventPublisher.publishEvent(new VehicleExitEvent(this,ticket.ticketId()));
        Receipt receipt = new Receipt(ticket.vehicle().registrationNumber(),fee,paymentType, LocalDateTime.now());
        logger.info("Receipt  generated for ticket [{}]",  ticket.ticketId());
        return receipt;
    }
}
