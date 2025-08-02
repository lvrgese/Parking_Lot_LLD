package org.parkinglot.service;

import org.springframework.stereotype.Component;

@Component
public class WelcomeService {

    public void printWelcome(){
        System.out.println("Welcome to smart parking system");
    }
}
