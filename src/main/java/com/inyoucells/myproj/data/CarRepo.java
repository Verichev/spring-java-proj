package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarRepo {
    private List<Car> cars = new ArrayList<>();
    private long idCounter = 0;

    public List<Car> getCars() {
        return cars;
    }

    public synchronized void removeCar(long id) {
        cars = cars.stream().filter(car -> car.getId() != id).collect(Collectors.toList());
    }

    public synchronized void removeCarsWithDriverId(long driverId) {
        cars = cars.stream().filter(car -> driverId != car.getDriverId()).collect(Collectors.toList());
    }

    public synchronized long addCar(Car car) {
        idCounter++;
        cars.add(new Car(idCounter, car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId()));
        return idCounter;
    }

    public synchronized void clean() {
        cars.clear();
        idCounter = 0;
    }
}
