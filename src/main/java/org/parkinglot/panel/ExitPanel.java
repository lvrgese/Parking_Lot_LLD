package org.parkinglot.panel;

import org.parkinglot.entity.ParkingTicket;
import org.parkinglot.entity.Reciept;
import org.parkinglot.event.VehicleExitEvent;
import org.parkinglot.payment.PaymentProcessor;
import org.parkinglot.payment.PaymentType;
import org.parkinglot.service.CostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExitPanel {

    private final CostCalculator costCalculator;
    private final PaymentProcessor paymentProcessor;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ExitPanel(CostCalculator costCalculator, PaymentProcessor paymentProcessor, ApplicationEventPublisher applicationEventPublisher) {
        this.costCalculator = costCalculator;
        this.paymentProcessor = paymentProcessor;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Reciept processVehicleExit(ParkingTicket ticket, PaymentType paymentType){
        if(ticket == null)
            return null;
        int fee = costCalculator.calculateFee(ticket);
        if(!paymentProcessor.processPayment(fee,paymentType)){
            throw new RuntimeException("Payment unsuccessful");
        }

        ticket.parkingSpot().setAvailable(true);

        applicationEventPublisher.publishEvent(new VehicleExitEvent(this,ticket.ticketId()));
        return new Reciept(ticket.vehicle().registrationNumber(),fee,paymentType, LocalDateTime.now());
    }
}
