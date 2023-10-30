package ply.cars.models.dtos;

import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    private String registration;
    private int yop;
    private String brand;
    private String model;
}
