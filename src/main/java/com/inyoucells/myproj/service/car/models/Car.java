package com.inyoucells.myproj.service.car.models;

import com.inyoucells.myproj.service.car.data.CarEntity;

import java.util.UUID;

import lombok.Data;

@Data
public class Car {
    private UUID id;
    private String brand;
    private String year;
    private boolean used;
    private int horsepower;
    private Long driverId;

    public Car(UUID id, String brand, String year, boolean used, int horsepower, Long driverId) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.used = used;
        this.horsepower = horsepower;
        this.driverId = driverId;
    }

    public Car(CarEntity carEntity) {
        id = carEntity.getUuid();
        brand = carEntity.getBrand();
        year = carEntity.getYear();
        used = carEntity.isUsed();
        horsepower = carEntity.getHorsepower();
        driverId = carEntity.getDriverId();
    }
}
