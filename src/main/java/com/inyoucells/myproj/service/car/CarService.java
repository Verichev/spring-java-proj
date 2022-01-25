package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;

import java.util.List;

public interface CarService {
    List<Car> getCars(long userId);

    void removeCar(long userId, long id);

    long addCar(long userId, Car car);
}
