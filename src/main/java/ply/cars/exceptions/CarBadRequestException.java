package ply.cars.exceptions;

public class CarBadRequestException extends RuntimeException {
    public CarBadRequestException(String msg){
        super(msg);
    }
}
