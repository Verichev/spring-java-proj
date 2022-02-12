package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getCars(long userId, long driverId) throws ServiceError;

    void removeCar(long userId, UUID id) throws ServiceError;

    UUID addCar(long userId, CarRequest car) throws ServiceError;

    List<Car> searchByBrand(long userId, String keyword) throws ServiceError;

    List<Car> getCarsByYearAndBrand(long userId, String year, String brand) throws ServiceError;

    List<Car> getCarsWitHorsepowerMore(long userId, int minHorsePower) throws ServiceError;
}
