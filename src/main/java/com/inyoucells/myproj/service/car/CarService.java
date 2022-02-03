package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.errors.ServiceError;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getCars(long userId, long driverId) throws ServiceError;

    void removeCar(long userId, long id) throws ServiceError;

    UUID addCar(long userId, Car car) throws ServiceError;

    List<Car> searchByBrand(long userId, String keyword) throws ServiceError;

    List<Car> getCarsByYearAndBrand(long userId, String year, String brand) throws ServiceError;

    List<Car> getCarsWitHorsepowerMore(long userId, int minHorsePower) throws ServiceError;
}
