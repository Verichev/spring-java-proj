package com.inyoucells.myproj.data;

import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;

public class CarFakeProvider {

    private final int startIndex;
    private int index;

    public CarFakeProvider(int startIndex) {
        this.startIndex = startIndex;
        index = startIndex;
    }

    public Car generateCar() {
        index++;
        return new Car(null, "brand:" + index, "year:" + index, false, index * 5, index * 10L);
    }

    public CarRequest generateCarRequest() {
        index++;
        return new CarRequest("brand:" + index, "year:" + index, false, index * 5, index * 10L);
    }

    public void reset() {
        index = startIndex;
    }
}
