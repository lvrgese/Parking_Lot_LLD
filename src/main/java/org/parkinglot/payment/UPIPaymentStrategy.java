package org.parkinglot.payment;

import org.springframework.stereotype.Component;

@Component
public class UPIPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(int cost) {

        System.out.println("Amount "+cost+" INR paid through UPI");
        return true;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.UPI;
    }
}
