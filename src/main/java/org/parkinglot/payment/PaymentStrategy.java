package org.parkinglot.payment;

public interface PaymentStrategy {

    boolean processPayment(int cost);

    PaymentType getType();

}
