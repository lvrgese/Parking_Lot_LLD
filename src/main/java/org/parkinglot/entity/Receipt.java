package org.parkinglot.entity;

import org.parkinglot.payment.PaymentType;

import java.time.LocalDateTime;

public record Receipt(String vehicleNumber, double cost, PaymentType paymentType, LocalDateTime paymentTime)
{ }
