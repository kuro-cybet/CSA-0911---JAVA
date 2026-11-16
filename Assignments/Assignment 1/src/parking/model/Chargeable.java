package parking.model;

public interface Chargeable {
    double CHARGING_RATE_PER_KWH = 12.0;
    double chargingFee(int kiloWattHours);
}
