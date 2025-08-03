package org.parkinglot.bootstrap;

import org.parkinglot.config.AppConfig;
import org.parkinglot.entity.*;
import org.parkinglot.panel.EntryPanel;
import org.parkinglot.panel.ExitPanel;
import org.parkinglot.payment.PaymentType;
import org.parkinglot.service.ParkingFloor;
import org.parkinglot.service.ParkingLot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        simulateParkingLot(context);
    }


    public static void simulateParkingLot(ApplicationContext context){
        logger.info("Welcome to smart parking lot system");

        ParkingLot lot = context.getBean(ParkingLot.class);

        //Adding floors
        ParkingFloor f1= lot.addParkingFloor();
        ParkingFloor f2 = lot.addParkingFloor();

        //Adding spots to each floor
        ParkingSpot p1 =f1.addNewParkingSpot(SpotType.S);
        ParkingSpot p2 =f1.addNewParkingSpot(SpotType.M);
        ParkingSpot p3 =f1.addNewParkingSpot(SpotType.L);
        ParkingSpot p4 =f1.addNewParkingSpot(SpotType.L);
        ParkingSpot p5 =f1.addNewParkingSpot(SpotType.XL);

        ParkingSpot p6 =f2.addNewParkingSpot(SpotType.S);
        ParkingSpot p7 =f2.addNewParkingSpot(SpotType.M);
        ParkingSpot p8 =f2.addNewParkingSpot(SpotType.M);
        ParkingSpot p9 =f2.addNewParkingSpot(SpotType.L);
        ParkingSpot p10 =f2.addNewParkingSpot(SpotType.L);

        //Panels
        EntryPanel entry = context.getBean(EntryPanel.class);
        ExitPanel exit = context.getBean(ExitPanel.class);

        //Park and Exit
        ParkingTicket t1 = entry.parkNewVehicle("KL-66-C-6874", VehicleType.TWO_WHEELER);
        ParkingTicket t2 = entry.parkNewVehicle("KL-02-C-5678", VehicleType.COMPACT_CAR);
        ParkingTicket t3 = entry.parkNewVehicle("KL-78-A-6874", VehicleType.TWO_WHEELER); //Should be in Floor-2
        ParkingTicket t4 = entry.parkNewVehicle("KA-02-T-0001", VehicleType.HEAVY_VEHICLE); //F1
        ParkingTicket t5 = entry.parkNewVehicle("KA-02-Y-0002", VehicleType.HEAVY_VEHICLE); //Should error out as there is no spot left

        //Floor Maintenance Check
        f2.setFloorUnderMaintenance();//Should error out as a bike is in f2

        exit.releaseVehicle(t3, PaymentType.CARD);

        f2.setFloorUnderMaintenance(); //Should work as bike in f2 is removed

        ParkingTicket t6 = entry.parkNewVehicle("KL-78-A-6874", VehicleType.TWO_WHEELER); //Should error out.F2 is in maintenance

        f2.setFloorActive();

        ParkingTicket t7 = entry.parkNewVehicle("KL-78-A-6874", VehicleType.TWO_WHEELER);

        //Exit flow and payment processing
        Receipt r1 = exit.releaseVehicle(t1,PaymentType.CASH);
        Receipt r2 = exit.releaseVehicle(t2,PaymentType.UPI);
        Receipt r3= exit.releaseVehicle(t1,PaymentType.CASH);//T1 is already removed.Should error out

    }
}