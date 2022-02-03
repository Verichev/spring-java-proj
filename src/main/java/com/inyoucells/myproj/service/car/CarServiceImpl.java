package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.errors.ServiceError;
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
    public void removeCar(long userId, long carId) throws ServiceError {
        carRepo.removeCar(userId, carId);
    }

    @Override
    public UUID addCar(long userId, Car car) throws ServiceError {
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
