package parking.model;

public class EBike extends Vehicle {
    public static final double FLAT_SESSION_FEE = 25.0;
    public EBike(String registrationNumber) { super(registrationNumber); }
    @Override public double hourlyRate() { return 0.0; }
}
