package ply.cars.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ply.cars.exceptions.CarBadRequestException;
import ply.cars.exceptions.CarNotFoundException;

@ControllerAdvice
public class CarExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String carNotFoundHandler(CarNotFoundException exc) {
        return exc.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CarBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String carBadRequestExceptionHandler(CarBadRequestException exc){
        return exc.getMessage();
    }
}
