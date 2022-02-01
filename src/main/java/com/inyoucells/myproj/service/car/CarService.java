package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.ServiceError;

import java.util.List;

public interface CarService {
    List<Car> getCars(long userId, long driverId) throws ServiceError;

    void removeCar(long userId, long id) throws ServiceError;

    long addCar(long userId, Car car) throws ServiceError;
}
