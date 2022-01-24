package com.inyoucells.myproj.models;

import lombok.Data;

@Data
public class Car {
    private long id;
    private String brand;
    private String year;
    private boolean used;
    private int horsepower;
    private Long driverId;

    public Car(long id, String brand, String year, boolean used, int horsepower, Long driverId) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.used = used;
        this.horsepower = horsepower;
        this.driverId = driverId;
    }
}
