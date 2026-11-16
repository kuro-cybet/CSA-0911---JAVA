package parking.facility;

import parking.model.Vehicle;

public class ParkingFacility {
    private final ParkingFloor[] floors;

    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException("At least one floor is required.");
        }
        floors = new ParkingFloor[slotsPerFloor.length];
        for (int i = 0; i < slotsPerFloor.length; i++) {
            floors[i] = new ParkingFloor(i, slotsPerFloor[i]);
        }
    }

    public int[] allocate(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Vehicle is required.");
        for (ParkingFloor floor : floors) {
            for (int s = 0; s < floor.getSlotCount(); s++) {
                ParkingSlot slot = floor.getSlot(s);
                if (slot.isFree()) {
                    slot.park(vehicle);
                    return new int[]{floor.getFloorNumber(), s};
                }
            }
        }
        return null;
    }

    public void release(int floorNumber, int slotNumber) {
        if (floorNumber < 0 || floorNumber >= floors.length) {
            throw new IllegalArgumentException("Invalid floor number.");
        }
        floors[floorNumber].getSlot(slotNumber).release();
    }

    public int getFloorCount() { return floors.length; }

    public int getTotalCapacity() {
        int total = 0;
        for (ParkingFloor floor : floors) total += floor.getSlotCount();
        return total;
    }

    public int getOccupiedCount() {
        int count = 0;
        for (ParkingFloor floor : floors)
            for (int s = 0; s < floor.getSlotCount(); s++)
                if (!floor.getSlot(s).isFree()) count++;
        return count;
    }
}
