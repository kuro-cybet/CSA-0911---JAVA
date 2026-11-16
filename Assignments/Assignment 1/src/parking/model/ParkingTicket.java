package parking.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTicket {
    private final long ticketId;
    private final Vehicle vehicle;
    private final int floorNumber;
    private final int slotNumber;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amountPaid;

    public ParkingTicket(long ticketId, Vehicle vehicle, int floorNumber,
                         int slotNumber, LocalDateTime entryTime) {
        if (ticketId <= 0 || vehicle == null || floorNumber < 0 || slotNumber < 0 || entryTime == null) {
            throw new IllegalArgumentException("Invalid ticket details.");
        }
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
        this.entryTime = entryTime;
    }

    public long getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getAmountPaid() { return amountPaid; }

    public long durationMinutes(LocalDateTime exit) {
        if (exit == null || exit.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }
        return Duration.between(entryTime, exit).toMinutes();
    }

    public void close(LocalDateTime exit, double amount) {
        if (exitTime != null) throw new IllegalStateException("Ticket is already closed.");
        if (exit == null || exit.isBefore(entryTime) || amount < 0) {
            throw new IllegalArgumentException("Invalid exit details.");
        }
        exitTime = exit;
        amountPaid = amount;
    }
}
