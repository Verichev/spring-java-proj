package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.models.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;

    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public List<Car> getCars(long userId) {
        return carRepo.getCars();
    }

    @Override
    public void removeCar(long userId, long id) {
        carRepo.removeCar(id);
    }

    @Override
    public long addCar(long userId, Car car) {
        return carRepo.addCar(car);
    }
}
