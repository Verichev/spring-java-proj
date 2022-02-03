package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Car;

import java.util.UUID;

public class CarFakeProvider {

    private final int startIndex;
    private int index;

    public CarFakeProvider(int startIndex) {
        this.startIndex = startIndex;
        index = startIndex;
    }

    public Car generateCar() {
        index++;
        return new Car(UUID.randomUUID(), "brand:" + index, "year:" + index, false, index * 5, index * 10L);
    }

    public void reset() {
        index = startIndex;
    }
}
