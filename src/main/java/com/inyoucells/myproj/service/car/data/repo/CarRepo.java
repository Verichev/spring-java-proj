package com.inyoucells.myproj.service.car.data.repo;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;
import com.inyoucells.myproj.service.car.data.CarEntity;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;
import com.inyoucells.myproj.service.driver.data.DriverEntity;
import com.inyoucells.myproj.service.driver.data.repo.DriverJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarRepo {
    private final CarJpaRepository carJpaRepository;
    private final DriverJpaRepository driverJpaRepository;
    private final CustomCarRepo customCarRepo;

    @Autowired
    public CarRepo(CarJpaRepository carJpaRepository, DriverJpaRepository driverJpaRepository, CustomCarRepo customCarRepo) {
        this.carJpaRepository = carJpaRepository;
        this.driverJpaRepository = driverJpaRepository;
        this.customCarRepo = customCarRepo;
    }

    public List<Car> searchByBrand(long userId, String keyword) {
        return carJpaRepository.searchCarsByBrand(keyword).stream().map(Car::new).collect(Collectors.toList());
    }

    public List<Car> getCarsByYearAndBrand(long userId, String year, String brand) {
        return carJpaRepository.findByYearAndBrand(year, brand).stream().map(Car::new).collect(Collectors.toList());
    }

    public List<Car> getCarsWitHorsepowerMore(long userId, int minHorsePower) {
        return customCarRepo.selectCarsWitHorsepowerMore(minHorsePower).stream().map(Car::new).collect(Collectors.toList());
    }

    public List<Car> getCars(long userId, long driverId) throws ServiceError {
        Optional<DriverEntity> driverUserId = driverJpaRepository.findById(driverId);
        if (driverUserId.isEmpty()) {
            throw new ServiceError(TypicalError.DRIVER_ID_NOT_FOUND);
        } else if (driverUserId.get().getUserId() != userId) {
            throw new ServiceError(TypicalError.NOT_AUTHORISED);
        }
        return carJpaRepository.findAllByDriverId(driverId).stream().map(Car::new).collect(Collectors.toList());
    }

    public void removeCar(long userId, UUID carId) throws ServiceError {
        Optional<CarEntity> carEntity = carJpaRepository.findById(carId);
        if (carEntity.isEmpty()) {
            throw new ServiceError(TypicalError.CAR_ID_NOT_FOUND);
        } else {
            Optional<DriverEntity> driverUserId = driverJpaRepository.findById(carEntity.get().getDriverId());
            if (driverUserId.isEmpty() || driverUserId.get().getUserId() != userId) {
                throw new ServiceError(TypicalError.NOT_AUTHORISED);
            }
        }
        carJpaRepository.delete(carEntity.get());
    }

    public void removeCarsWithDriverId(long userId, long driverId) throws ServiceError {
        Optional<DriverEntity> driverUserId = driverJpaRepository.findById(driverId);
        if (driverUserId.isEmpty()) {
            throw new ServiceError(TypicalError.DRIVER_ID_NOT_FOUND);
        } else if (driverUserId.get().getUserId() != userId) {
            throw new ServiceError(TypicalError.NOT_AUTHORISED);
        }
        carJpaRepository.deleteByDriverId(driverId);
    }

    public UUID addCar(long userId, CarRequest car) throws ServiceError {
        Optional<DriverEntity> driverUserId = driverJpaRepository.findById(car.getDriverId());
        if (driverUserId.isEmpty()) {
            throw new ServiceError(TypicalError.DRIVER_ID_NOT_FOUND);
        } else if (driverUserId.get().getUserId() != userId) {
            throw new ServiceError(TypicalError.NOT_AUTHORISED);
        }
        CarEntity result = carJpaRepository.save(
                new CarEntity(null, car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(),
                        car.getDriverId()));
        return result.getUuid();
    }

    public void clean() {
        carJpaRepository.deleteAll();
    }
}
