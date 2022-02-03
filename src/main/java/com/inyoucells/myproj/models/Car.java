package com.inyoucells.myproj.models;

import com.inyoucells.myproj.data.entity.CarEntity;
import lombok.Data;

import java.util.UUID;

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
