package com.inyoucells.myproj.service.driver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.driver.data.repo.DriverRepo;
import com.inyoucells.myproj.service.driver.models.Driver;
import com.inyoucells.myproj.service.driver.models.DriverDetail;
import com.inyoucells.myproj.service.driver.models.DriverRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

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
        Mockito.doReturn(Collections.emptyList()).when(driverRepo).getDrivers(-1, 0, 10);
        List<Driver> result = driverService.getDrivers(-1, 0, 10);

        Mockito.verify(driverRepo).getDrivers(-1, 0, 10);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getDriversFull() {
        Mockito.doReturn(Collections.emptyList()).when(driverRepo).getDriversFull(-1, 0, 10);
        List<DriverDetail> result = driverService.getDriversFull(-1, 0, 10);

        Mockito.verify(driverRepo).getDriversFull(-1, 0, 10);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void removeCar() throws ServiceError {
        driverService.removeDriver(-1, 3);

        Mockito.verify(driverRepo).removeDriver(-1, 3);
    }

    @Test
    void addCar() {
        DriverRequest driver = new DriverRequest("testName", "textLicence");
        driverService.addDriver(-1, driver);

        Mockito.verify(driverRepo).addDriver(-1, driver);
    }
}