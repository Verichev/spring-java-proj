package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.DriverEntity;
import com.inyoucells.myproj.data.jpa.DriverJpaRepository;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.models.ServiceError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DriverRepoTest {

    @Mock
    private DriverJpaRepository driverJpaRepository;

    private final CarFakeProvider carFakeProvider = new CarFakeProvider(0);
    private DriverRepo driverRepo;

    @BeforeEach
    void setup() {
        driverRepo = new DriverRepo(driverJpaRepository);
    }

    @Test
    void getDriversFull() {
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 1);
        driverEntity.setCars(Collections.emptyList());
        Driver driver = new Driver(driverEntity, true);
        Mockito.doReturn(Collections.singletonList(driverEntity)).when(driverJpaRepository).findAllByUserId(10L);
        assertEquals(Collections.singletonList(driver), driverRepo.getDriversFull(10L));
    }

    @Test
    void getDrivers() {
        Driver driver = new Driver("John", "licence1");
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 1);
        Mockito.doReturn(Collections.singletonList(driverEntity)).when(driverJpaRepository).findAllByUserId(10L);
        assertEquals(Collections.singletonList(driver), driverRepo.getDrivers(10L));
    }

    @Test
    void removeDriver_noDriverId() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findById(999L);
        ServiceError error = assertThrows(
                ServiceError.class, () -> driverRepo.removeDriver(100, 999));
        assertEquals(new ServiceError(HttpError.BAD_REQUEST), error);
    }

    @Test
    void removeDriver_userIdDoesntMatch() {
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 10);
        Mockito.doReturn(Optional.of(driverEntity)).when(driverJpaRepository).findById(999L);
        ServiceError error = assertThrows(
                ServiceError.class, () -> driverRepo.removeDriver(100, 999));
        assertEquals(new ServiceError(HttpError.NOT_AUTHORISED), error);
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
        Driver driver = new Driver("John", "licence1");
        DriverEntity resultEntity = new DriverEntity("John", "licence1", 10);
        resultEntity.setId(1000L);
        DriverEntity driverEntity = new DriverEntity("John", "licence1", 10);
        Mockito.doReturn(resultEntity).when(driverJpaRepository).save(driverEntity);

        assertEquals(1000L, driverRepo.addDriver(10, driver));
        Mockito.verify(driverJpaRepository).save(driverEntity);
    }
}