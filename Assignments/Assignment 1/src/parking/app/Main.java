package parking.app;

import parking.facility.ParkingFacility;
import parking.model.*;
import parking.service.ParkingService;
import parking.service.PricingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static ParkingService service;

    public static void main(String[] args) {
        ParkingFacility facility = new ParkingFacility(new int[]{5, 4, 6});
        service = new ParkingService(facility, new PricingService());

        System.out.println("CAMPUS SMART-PARKING SYSTEM");
        System.out.println("1. Run demonstration");
        System.out.println("2. Interactive menu");
        System.out.print("Choose: ");
        String choice = SCANNER.nextLine().trim();

        if ("1".equals(choice)) runDemo();
        else runMenu();
    }

    private static void runDemo() {
        LocalDateTime entry = LocalDate.now().atTime(10, 0);
        Vehicle bike = new TwoWheeler("TN01AB1111");
        Vehicle car = new Car("TN02CD2222");
        Vehicle ev = new ElectricCar("TN03EF3333");

        ParkingTicket t1 = service.enter(bike, entry);
        ParkingTicket t2 = service.enter(car, entry);
        ParkingTicket t3 = service.enter(ev, entry);

        System.out.printf("Ticket %d: %s at floor %d, slot %d%n",
                t1.getTicketId(), bike, t1.getFloorNumber(), t1.getSlotNumber());
        System.out.printf("Ticket %d: %s at floor %d, slot %d%n",
                t2.getTicketId(), car, t2.getFloorNumber(), t2.getSlotNumber());
        System.out.printf("Ticket %d: %s at floor %d, slot %d%n",
                t3.getTicketId(), ev, t3.getFloorNumber(), t3.getSlotNumber());

        System.out.printf("Two-wheeler fee: Rs %.2f%n",
                service.exit("TN01AB1111", entry.plusMinutes(61), 0));
        System.out.printf("Car fee: Rs %.2f%n",
                service.exit("TN02CD2222", entry.plusMinutes(130), 0));
        System.out.printf("EV fee including 10 kWh: Rs %.2f%n",
                service.exit("TN03EF3333", entry.plusMinutes(130), 10));
        System.out.println("Occupied slots: " + service.getFacility().getOccupiedCount());
    }

    private static void runMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Park vehicle");
            System.out.println("2. Exit vehicle");
            System.out.println("3. Show occupancy");
            System.out.println("4. Exit application");
            System.out.print("Select: ");
            String option = SCANNER.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> parkVehicle();
                    case "2" -> exitVehicle();
                    case "3" -> showOccupancy();
                    case "4" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (RuntimeException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
    }

    private static void parkVehicle() {
        System.out.println("Vehicle type: 1 Two-wheeler | 2 Car | 3 Electric car | 4 E-bike | 5 Delivery van");
        System.out.print("Type: ");
        String type = SCANNER.nextLine().trim();
        System.out.print("Registration number: ");
        String registration = SCANNER.nextLine().trim();

        Vehicle vehicle = switch (type) {
            case "1" -> new TwoWheeler(registration);
            case "2" -> new Car(registration);
            case "3" -> new ElectricCar(registration);
            case "4" -> new EBike(registration);
            case "5" -> new DeliveryVan(registration);
            default -> throw new IllegalArgumentException("Unknown vehicle type.");
        };

        ParkingTicket ticket = service.enter(vehicle, LocalDateTime.now());
        System.out.printf("Ticket %d issued. Floor %d, slot %d.%n",
                ticket.getTicketId(), ticket.getFloorNumber(), ticket.getSlotNumber());
    }

    private static void exitVehicle() {
        System.out.print("Registration number: ");
        String registration = SCANNER.nextLine().trim();
        System.out.print("Charging units (enter 0 if none): ");
        int units = Integer.parseInt(SCANNER.nextLine().trim());
        double fee = service.exit(registration, LocalDateTime.now(), units);
        System.out.printf("Exit completed. Amount due: Rs %.2f%n", fee);
    }

    private static void showOccupancy() {
        int occupied = service.getFacility().getOccupiedCount();
        int total = service.getFacility().getTotalCapacity();
        System.out.printf("Occupied: %d | Available: %d | Capacity: %d%n",
                occupied, total - occupied, total);
    }
}
