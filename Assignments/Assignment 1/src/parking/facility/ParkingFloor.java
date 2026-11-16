package parking.facility;

public class ParkingFloor {
    private final int floorNumber;
    private final ParkingSlot[] slots;

    public ParkingFloor(int floorNumber, int slotCount) {
        if (floorNumber < 0 || slotCount <= 0) throw new IllegalArgumentException("Invalid floor configuration.");
        this.floorNumber = floorNumber;
        this.slots = new ParkingSlot[slotCount];
        for (int i = 0; i < slotCount; i++) slots[i] = new ParkingSlot(i);
    }

    public int getFloorNumber() { return floorNumber; }
    public int getSlotCount() { return slots.length; }
    public ParkingSlot getSlot(int index) {
        if (index < 0 || index >= slots.length) throw new IndexOutOfBoundsException("Invalid slot.");
        return slots[index];
    }
}
