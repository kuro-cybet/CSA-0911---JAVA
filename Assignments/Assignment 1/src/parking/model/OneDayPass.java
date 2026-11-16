package parking.model;

import java.time.LocalDate;

public class OneDayPass implements Pass {
    private final LocalDate validDate;

    public OneDayPass(LocalDate validDate) {
        if (validDate == null) throw new IllegalArgumentException("Pass date is required.");
        this.validDate = validDate;
    }

    @Override
    public boolean isValidOn(LocalDate date) {
        return validDate.equals(date);
    }
}
