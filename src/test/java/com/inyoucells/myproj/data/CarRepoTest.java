package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CarRepoTest {

    private CarRepo carRepo;
    private CarFakeProvider carFakeProvider;

    @BeforeEach
    void setup() {
        carRepo = new CarRepo();
        carFakeProvider = new CarFakeProvider(0);
    }

    @Test
    void getCars() {
        Car car = carFakeProvider.generateCar();
        carRepo.addCar(car);
        assertEquals(Collections.singletonList(car), carRepo.getCars());
    }

    @Test
    void removeCar() {
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        carRepo.addCar(car1);
        carRepo.addCar(car2);

        carRepo.removeCar(2);
        assertEquals(Collections.singletonList(car1), carRepo.getCars());
    }

    @Test
    void removeCarsWithDriverIds() {
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        Car car3 = carFakeProvider.generateCar();
        carRepo.addCar(car1);
        carRepo.addCar(car2);
        carRepo.addCar(car3);

        assertEquals(carRepo.getCars().get(1), car2);
        carRepo.removeCarsWithDriverId(car2.getDriverId());
        assertNotEquals(carRepo.getCars().get(1), car2);
    }

    @Test
    void addCar() {
        Car car = carFakeProvider.generateCar();
        carRepo.addCar(car);
        assertEquals(Collections.singletonList(car), carRepo.getCars());
    }
}