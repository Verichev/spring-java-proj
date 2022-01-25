package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DriverRepoTest {

    private DriverRepo driverRepo;
    @Mock
    private CarRepo carRepo;

    @BeforeEach
    void setup() {
        driverRepo = new DriverRepo(carRepo);
    }

    @Test
    void getDrivers() {
        Driver driver = new Driver(1, "John", "licence1");
        driverRepo.addDriver(driver);
        assertEquals(Collections.singletonList(driver), driverRepo.getDrivers());
    }

    @Test
    void removeCar() {
        Driver driver1 = new Driver(1, "John", "licence1");
        Driver driver2 = new Driver(2, "Bob", "licence2");
        driverRepo.addDriver(driver1);
        driverRepo.addDriver(driver2);

        driverRepo.removeDriver(2);
        assertEquals(Collections.singletonList(driver1), driverRepo.getDrivers());
        Mockito.verify(carRepo).removeCarsWithDriverId(2);
    }

    @Test
    void addCar() {
        Driver driver = new Driver(1, "John", "licence1");
        driverRepo.addDriver(driver);
        assertEquals(Collections.singletonList(driver), driverRepo.getDrivers());
    }
}