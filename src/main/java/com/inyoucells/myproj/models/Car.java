package com.inyoucells.myproj.models;

import lombok.Data;

import java.util.Objects;

@Data
public class Car {
    private long id;
    private String brand;
    private String year;
    private boolean used;
    private int horsepower;

    public Car(long id, String brand, String year, boolean used, int horsepower) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.used = used;
        this.horsepower = horsepower;
    }

    public Car(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
