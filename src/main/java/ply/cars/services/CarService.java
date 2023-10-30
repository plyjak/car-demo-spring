package ply.cars.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ply.cars.models.entities.Car;
import ply.cars.repositories.CarRepository;

/*
 * To be completly honest I don't think creating a service here is the best approach
 * Since it is used just for filtering out 'deleted' entities from CarRepository,
 * but since my DB is so simple, I decided to implement it this way. In a 'real world'
 * application I wouldn't use a Service pattern here, Repository would be sufficient.
 */
@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> findAllNonDeleted(){
        return carRepository.findAll().stream()
            .filter(
                it -> it.getDeletedOn() == null)
            .collect(Collectors.toList());
    }

    public Optional<Car> findByIdNonDeleted(Long id){
        return carRepository.findById(id)
            .flatMap(it -> 
                it.getDeletedOn() == null ? Optional.of(it) : Optional.empty()
            );
    }

    public List<Car> filterNonDeleted(
        int minYop,
        int maxYop,
        String registrationPart,
        String brand,
        String model
    ){
        return carRepository.filter(
            minYop, 
            maxYop, 
            registrationPart, 
            brand, 
            model)
        .stream()
        .filter(it -> it.getDeletedOn() == null)
        .collect(Collectors.toList());
    }
}
