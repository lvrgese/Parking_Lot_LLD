package org.parkinglot.payment;

import org.springframework.stereotype.Component;

@Component
public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(int cost) {

        System.out.println("Amount "+cost+" INR paid via CASH");
        return true;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.CASH;
    }
}
