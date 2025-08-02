package org.parkinglot.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentProcessor {

    private PaymentStrategy paymentStrategy;

    private final Map<PaymentType,PaymentStrategy> strategies;

    @Autowired
    public PaymentProcessor(List<PaymentStrategy> strategies) {
        this.strategies = new HashMap<>();

        for(PaymentStrategy p : strategies){
            this.strategies.put(p.getType(),p);
        }
    }

    public boolean processPayment(int amount, PaymentType type ){
        if(!strategies.containsKey(type)){
            System.err.println("Invalid payment type");
        }

        return strategies.get(type).processPayment(amount);
    }
}
