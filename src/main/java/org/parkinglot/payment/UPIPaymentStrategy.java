package org.parkinglot.payment;

import org.springframework.stereotype.Component;

@Component
public class UPIPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(int cost) {
        return true;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.UPI;
    }
}
