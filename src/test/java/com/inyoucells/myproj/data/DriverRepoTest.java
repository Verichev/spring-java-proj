package com.inyoucells.myproj.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.driver.data.DriverEntity;
import com.inyoucells.myproj.service.driver.data.repo.DriverJpaRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DriverRepoTest {

    @Mock
    private DriverJpaRepository driverJpaRepository;

    private DriverRepo driverRepo;

    @BeforeEach
    void setup() {
        driverRepo = new DriverRepo(driverJpaRepository);
    }

    @Test
    void getDriversFull() {
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 1);
        driverEntity.setCars(Collections.emptyList());
        DriverDetail driver = new DriverDetail("John", "licence1");
        driver.setCars(Collections.emptyList());
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.doReturn(Collections.singletonList(driverEntity)).when(driverJpaRepository).findAllByUserId(10L, pageable);
        assertEquals(Collections.singletonList(driver), driverRepo.getDriversFull(10L, 0, 10));
    }

    @Test
    void getDrivers() {
        Driver driver = new Driver("John", "licence1");
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 1);
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.doReturn(Collections.singletonList(driverEntity)).when(driverJpaRepository).findAllByUserId(10L, pageable);
        assertEquals(Collections.singletonList(driver), driverRepo.getDrivers(10L, 0, 10));
    }

    @Test
    void removeDriver_noDriverId() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findById(999L);
        ServiceError error = assertThrows(
                ServiceError.class, () -> driverRepo.removeDriver(100, 999));
        assertEquals(new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND), error);
    }

    @Test
    void removeDriver_userIdDoesntMatch() {
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 10);
        Mockito.doReturn(Optional.of(driverEntity)).when(driverJpaRepository).findById(999L);
        ServiceError error = assertThrows(
                ServiceError.class, () -> driverRepo.removeDriver(100, 999));
        assertEquals(new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.NOT_AUTHORISED), error);
    }

    @Test
    void removeDriver() {
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 100);
        Mockito.doReturn(Optional.of(driverEntity)).when(driverJpaRepository).findById(999L);
        assertDoesNotThrow(() -> driverRepo.removeDriver(100, 999));
        Mockito.verify(driverJpaRepository).deleteById(999L);
    }

    @Test
    void addDriver() {
        DriverRequest driver = new DriverRequest("John", "licence1");
        DriverEntity resultEntity = new DriverEntity("John", "licence1", 10);
        resultEntity.setId(1000L);
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 10);
        Mockito.doReturn(resultEntity).when(driverJpaRepository).save(driverEntity);

        assertEquals(1000L, driverRepo.addDriver(10, driver));
        Mockito.verify(driverJpaRepository).save(driverEntity);
    }
}