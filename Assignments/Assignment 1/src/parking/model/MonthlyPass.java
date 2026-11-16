package parking.model;

import java.time.LocalDate;

public class MonthlyPass implements Pass {
    private final String holderRegistration;
    private final LocalDate validFrom;
    private final LocalDate validUntil;

    public MonthlyPass(String holderRegistration, LocalDate validFrom, LocalDate validUntil) {
        if (holderRegistration == null || holderRegistration.isBlank()
                || validFrom == null || validUntil == null || validUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException("Invalid monthly pass details.");
        }
        this.holderRegistration = holderRegistration.trim().toUpperCase();
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public String getHolderRegistration() { return holderRegistration; }

    @Override
    public boolean isValidOn(LocalDate date) {
        return date != null && !date.isBefore(validFrom) && !date.isAfter(validUntil);
    }
}
