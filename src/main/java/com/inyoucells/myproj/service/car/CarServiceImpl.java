package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.car.data.repo.CarRepo;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;
import com.inyoucells.myproj.service.driver.data.repo.DriverRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;

    @Autowired
    DriverRepo driverRepo;

    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public List<Car> getCars(long userId, long driverId) throws ServiceError {
        return carRepo.getCars(userId, driverId);
    }

    @Override
    public void removeCar(long userId, UUID carId) throws ServiceError {
        carRepo.removeCar(userId, carId);
    }

    @Override
    public UUID addCar(long userId, CarRequest car) throws ServiceError {
        return carRepo.addCar(userId, car);
    }

    public List<Car> searchByBrand(long userId, String keyword) {
        return carRepo.searchByBrand(userId, keyword);
    }

    public List<Car> getCarsByYearAndBrand(long userId, String year, String brand) {
        return carRepo.getCarsByYearAndBrand(userId, year, brand);
    }

    public List<Car> getCarsWitHorsepowerMore(long userId, int minHorsePower) {
        return carRepo.getCarsWitHorsepowerMore(userId, minHorsePower);
    }
}
