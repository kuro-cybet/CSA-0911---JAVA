package parking.service;

import parking.model.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PricingService {
    private static final double DAILY_MAXIMUM = 300.0;

    public double calculate(Vehicle vehicle, long minutes, LocalDateTime entry,
                            LocalDateTime exit, Pass pass, int chargingKwh) {
        if (vehicle == null || entry == null || exit == null || minutes < 0) {
            throw new IllegalArgumentException("Invalid pricing input.");
        }

        if (pass != null && pass.isValidOn(entry.toLocalDate())) {
            if (!(pass instanceof MonthlyPass monthly)
                    || monthly.getHolderRegistration().equals(vehicle.getRegistrationNumber())) {
                return 0.0;
            }
        }

        if (vehicle instanceof EBike) return EBike.FLAT_SESSION_FEE;

        double fee;
        if (vehicle instanceof DeliveryVan) {
            fee = deliveryVanFee(minutes, entry, exit);
        } else {
            fee = vehicle.parkingFee(minutes);
        }

        if (vehicle instanceof Chargeable chargeable) {
            fee += chargeable.chargingFee(chargingKwh);
        }
        return Math.min(fee, DAILY_MAXIMUM);
    }

    private double deliveryVanFee(long minutes, LocalDateTime entry, LocalDateTime exit) {
        // Split the stay into minute-sized intervals to apply the 9 AM–5 PM rule accurately.
        double fee = 0.0;
        LocalDateTime cursor = entry;
        while (cursor.isBefore(exit)) {
            LocalDateTime next = cursor.plusMinutes(1);
            boolean premium = !cursor.toLocalTime().isBefore(LocalTime.of(9, 0))
                    && cursor.toLocalTime().isBefore(LocalTime.of(17, 0));
            fee += premium ? 60.0 / 60.0 : 30.0 / 60.0;
            cursor = next;
        }
        long billableHours = (minutes + 59) / 60;
        // Delivery van fee is time-weighted per minute; bill each started hour by scaling
        // the average minute rate if the duration contains a partial hour.
        if (minutes == 0) return 0.0;
        return fee * (billableHours * 60.0 / minutes);
    }
}
