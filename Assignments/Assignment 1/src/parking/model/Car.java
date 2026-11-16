package parking.model;

public class Car extends Vehicle {
    public Car(String registrationNumber) { super(registrationNumber); }
    @Override public double hourlyRate() { return 30.0; }
}
