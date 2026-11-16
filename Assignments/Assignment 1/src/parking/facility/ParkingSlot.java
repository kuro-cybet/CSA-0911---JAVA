package parking.facility;

import parking.model.Vehicle;

public class ParkingSlot {
    private final int slotNumber;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotNumber) {
        if (slotNumber < 0) throw new IllegalArgumentException("Slot number cannot be negative.");
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() { return slotNumber; }
    public boolean isFree() { return parkedVehicle == null; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }

    public void park(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Vehicle is required.");
        if (!isFree()) throw new IllegalStateException("Slot is already occupied.");
        parkedVehicle = vehicle;
    }

    public void release() {
        if (isFree()) throw new IllegalStateException("Slot is already free.");
        parkedVehicle = null;
    }
}
