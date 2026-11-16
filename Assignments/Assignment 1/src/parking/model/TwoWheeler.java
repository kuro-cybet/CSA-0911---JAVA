package parking.model;

public class TwoWheeler extends Vehicle {
    public TwoWheeler(String registrationNumber) { super(registrationNumber); }
    @Override public double hourlyRate() { return 10.0; }
}
