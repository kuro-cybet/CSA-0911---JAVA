package parking.model;

import java.util.Objects;

public abstract class Vehicle {
    private final String registrationNumber;

    protected Vehicle(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number cannot be blank.");
        }
        this.registrationNumber = registrationNumber.trim().toUpperCase();
    }

    public final String getRegistrationNumber() {
        return registrationNumber;
    }

    public abstract double hourlyRate();

    public double parkingFee(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative.");
        long hours = (minutes + 59) / 60;
        return hours * hourlyRate();
    }

    @Override
    public final boolean equals(Object object) {
        return object instanceof Vehicle other
                && registrationNumber.equals(other.registrationNumber);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(registrationNumber);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + registrationNumber + "]";
    }
}
