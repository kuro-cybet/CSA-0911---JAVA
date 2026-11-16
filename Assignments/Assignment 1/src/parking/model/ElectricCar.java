package parking.model;

public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String registrationNumber) { super(registrationNumber); }

    @Override
    public double chargingFee(int kiloWattHours) {
        if (kiloWattHours < 0) throw new IllegalArgumentException("Charging units cannot be negative.");
        return kiloWattHours * CHARGING_RATE_PER_KWH;
    }
}
