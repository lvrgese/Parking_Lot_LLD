package org.parkinglot.bootstrap;

import org.parkinglot.config.AppConfig;
import org.parkinglot.service.CostCalculator;
import org.parkinglot.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        WelcomeService welcomeService = context.getBean(WelcomeService.class);
        welcomeService.printWelcome();

    }
}