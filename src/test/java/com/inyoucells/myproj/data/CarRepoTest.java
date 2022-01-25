package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarRepoTest {

    private CarRepo carRepo;

    @BeforeEach
    void setup() {
        carRepo = new CarRepo();
    }

    @Test
    void getCars() {
        Car car = new Car(1, "brand", "1988", true, 33, 1222L);
        carRepo.addCar(car);
        assertEquals(Collections.singletonList(car), carRepo.getCars());
    }

    @Test
    void removeCar() {
        Car car1 = new Car(1, "brand1", "1988", true, 33, 1222L);
        Car car2 = new Car(2, "brand2", "1988", true, 55, 1222L);
        carRepo.addCar(car1);
        carRepo.addCar(car2);

        carRepo.removeCar(2);
        assertEquals(Collections.singletonList(car1), carRepo.getCars());
    }

    @Test
    void removeCarsWithDriverIds() {
        Car car1 = new Car(1, "brand1", "1988", true, 33, 1222L);
        Car car2 = new Car(2, "brand2", "1988", true, 55, 1223L);
        Car car3 = new Car(3, "brand2", "1988", true, 55, 1227L);
        carRepo.addCar(car1);
        carRepo.addCar(car2);
        carRepo.addCar(car3);
        carRepo.removeCarsWithDriverId(1223L);
        assertEquals(Arrays.asList(car1, car3), carRepo.getCars());
    }

    @Test
    void addCar() {
        Car car = new Car(1, "brand1", "1988", true, 33, 1222L);
        carRepo.addCar(car);
        assertEquals(Collections.singletonList(car), carRepo.getCars());
    }
}