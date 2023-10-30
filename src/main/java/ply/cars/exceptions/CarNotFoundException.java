package ply.cars.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long id) {
        super("Could not find a car w/ id=" + id);
    }
}
