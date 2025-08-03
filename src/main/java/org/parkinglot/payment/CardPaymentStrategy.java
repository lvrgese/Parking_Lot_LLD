package org.parkinglot.payment;

import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(int cost) {

        System.out.println("Amount "+cost+" INR paid via CARD");
        return true;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.CARD;
    }
}
