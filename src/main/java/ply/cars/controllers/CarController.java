package ply.cars.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ply.cars.repositories.CarRepository;
import ply.cars.services.CarService;
import ply.cars.exceptions.CarBadRequestException;
import ply.cars.exceptions.CarNotFoundException;
import ply.cars.models.dtos.CarDTO;
import ply.cars.models.entities.Car;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class CarController {
    @Autowired
    private CarRepository carRepository;
    
    @Autowired
    private CarService carService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("api/cars")
    List<CarDTO> getAll(
        @RequestParam(defaultValue="-1") int minYop,
        @RequestParam(defaultValue="-1") int maxYop,
        @RequestParam(defaultValue="") String registration,
        @RequestParam(defaultValue="") String brand,
        @RequestParam(defaultValue="") String model
    ){
        return carService
            .filterNonDeleted(
                minYop,
                maxYop,
                registration.isEmpty() ? null : registration,
                brand.isEmpty() ? null : brand,
                model.isEmpty() ? null : model)
            .stream()
            .map(it -> modelMapper.map(it, CarDTO.class))
            .collect(Collectors.toList());
    }

    @GetMapping("api/cars/{id}")
    CarDTO getById(@PathVariable Long id){
        return carService
            .findByIdNonDeleted(id)
            .map(it -> 
                modelMapper.map(it, CarDTO.class))
            .orElseThrow(() -> 
                new CarNotFoundException(id)
            );
    }

    @PutMapping("api/cars/{id}")
    CarDTO updateCar(@RequestBody CarDTO newCarDTO, @PathVariable Long id){
        return carService.findByIdNonDeleted(id)
            .map(it -> {
                it.setRegistration(newCarDTO.getRegistration());
                it.setYop(newCarDTO.getYop());
                it.setModel(newCarDTO.getModel());
                it.setBrand(newCarDTO.getBrand());
                return modelMapper.map(carRepository.save(it), CarDTO.class);
            }).orElseThrow(() -> new CarNotFoundException(id));
    }

    @PostMapping("api/cars")
    CarDTO newCar(@RequestBody CarDTO newCarDTO) {
        Car mapped;
        try{
            mapped = modelMapper.map(newCarDTO, Car.class);
        }catch(ConfigurationException | MappingException exc){
            throw new CarBadRequestException("Your request couldn't be handled.");
        }

        try{
            return modelMapper.map(carRepository.save(mapped), CarDTO.class);
        }
        catch(Exception exc){
            throw new CarBadRequestException("Couldn't handle request");
        }
    }

    @DeleteMapping("api/cars/{id}")
    void deleteCar(@PathVariable Long id){
        carRepository.findById(id).ifPresent(it -> {
            it.setDeletedOn(LocalDateTime.now());
            carRepository.save(it);
        });
    }
}
