package com.inyoucells.myproj.service.car.models;

import lombok.Data;

@Data
public class CarRequest {
    private String brand;
    private String year;
    private boolean used;
    private int horsepower;
    private Long driverId;

    public CarRequest(String brand, String year, boolean used, int horsepower, Long driverId) {
        this.brand = brand;
        this.year = year;
        this.used = used;
        this.horsepower = horsepower;
        this.driverId = driverId;
    }
}
