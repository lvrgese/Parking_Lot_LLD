package org.parkinglot.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentProcessor {

    private PaymentStrategy paymentStrategy;
    private final Map<PaymentType,PaymentStrategy> strategies;
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

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
            return false;
        }
        logger.info("Selected payment strategy: [{}]", type.toString());
        boolean res = strategies.get(type).processPayment(amount);
        if( res) {
            logger.info("Processing payment of ₹{} using [{}] ", amount, type);
            return true;
        }
        logger.error("Payment failed via [{}] for amount [{}]", type,amount);
        return false;
    }
}
