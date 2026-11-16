package parking.service;

import parking.facility.ParkingFacility;
import parking.model.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingService {
    private static long nextTicketId = 1;

    private final ParkingFacility facility;
    private final PricingService pricingService;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();

    public ParkingService(ParkingFacility facility, PricingService pricingService) {
        if (facility == null || pricingService == null) throw new IllegalArgumentException("Dependencies are required.");
        this.facility = facility;
        this.pricingService = pricingService;
    }

    public ParkingTicket enter(Vehicle vehicle, LocalDateTime entryTime) {
        return enter(vehicle, entryTime, null);
    }

    public ParkingTicket enter(Vehicle vehicle, LocalDateTime entryTime, Pass pass) {
        if (vehicle == null || entryTime == null) throw new IllegalArgumentException("Vehicle and entry time are required.");
        String registration = vehicle.getRegistrationNumber();
        if (activeTickets.containsKey(registration)) throw new IllegalStateException("This vehicle is already parked.");

        int[] position = facility.allocate(vehicle);
        if (position == null) throw new IllegalStateException("No parking slot is available.");

        ParkingTicket ticket = new ParkingTicket(nextTicketId++, vehicle,
                position[0], position[1], entryTime);
        activeTickets.put(registration, ticket);
        return ticket;
    }

    public double exit(String registrationNumber, LocalDateTime exitTime, int chargingKwh) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required.");
        }
        String key = registrationNumber.trim().toUpperCase();
        ParkingTicket ticket = activeTickets.get(key);
        if (ticket == null) throw new IllegalStateException("No active ticket found.");

        long minutes = ticket.durationMinutes(exitTime);
        double fee = pricingService.calculate(ticket.getVehicle(), minutes,
                ticket.getEntryTime(), exitTime, null, chargingKwh);

        ticket.close(exitTime, fee);
        facility.release(ticket.getFloorNumber(), ticket.getSlotNumber());
        activeTickets.remove(key);
        return fee;
    }

    public ParkingTicket findActiveTicket(String registrationNumber) {
        if (registrationNumber == null) return null;
        return activeTickets.get(registrationNumber.trim().toUpperCase());
    }

    public int getActiveVehicleCount() { return activeTickets.size(); }
    public ParkingFacility getFacility() { return facility; }
}
