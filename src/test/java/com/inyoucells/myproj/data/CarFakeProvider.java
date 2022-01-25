package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Car;

public class CarFakeProvider {

    private int index;

    public CarFakeProvider(int startIndex) {
        index = startIndex;
    }

    public Car generateCar() {
        index++;
        return new Car(index, "brand:" + index, "year:" + index, false, index * 5, index * 10L);
    }
}
