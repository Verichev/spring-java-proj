package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private DriverRepo driverRepo;
    private DriverServiceImpl driverService;

    @BeforeEach
    void setup() {
        driverService = new DriverServiceImpl(driverRepo);
    }

    @Test
    void getDrivers() {
        Mockito.doReturn(Collections.emptyList()).when(driverRepo).getDrivers();
        List<Driver> result = driverService.getDrivers(-1);

        Mockito.verify(driverRepo).getDrivers();
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void removeCar() {
        driverService.removeDriver(-1, 3);

        Mockito.verify(driverRepo).removeDriver(3);
    }

    @Test
    void addCar() {
        Driver driver = new Driver(3, "testName", "textLicence");
        driverService.addDriver(-1, driver);

        Mockito.verify(driverRepo).addDriver(driver);
    }
}