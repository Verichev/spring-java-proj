package com.inyoucells.myproj.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.car.data.CarEntity;
import com.inyoucells.myproj.service.car.data.repo.CarJpaRepository;
import com.inyoucells.myproj.service.car.data.repo.CarRepo;
import com.inyoucells.myproj.service.car.data.repo.CustomCarRepo;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;
import com.inyoucells.myproj.service.driver.data.DriverEntity;
import com.inyoucells.myproj.service.driver.data.repo.DriverJpaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CarRepoTest {

    @Mock
    private CarJpaRepository carJpaRepository;
    @Mock
    private DriverJpaRepository driverJpaRepository;
    @Mock
    private CustomCarRepo customCarRepo;

    UUID uuid = UUID.randomUUID();

    private CarRepo carRepo;
    private CarFakeProvider carFakeProvider;

    @BeforeEach
    void setup() {
        carRepo = new CarRepo(carJpaRepository, driverJpaRepository, customCarRepo);
        carFakeProvider = new CarFakeProvider(0);
    }

    @Test
    void getCars() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Collections.singletonList(carEntity)).when(carJpaRepository).findAllByDriverId(999L);
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(999L);
        List<Car> cars = assertDoesNotThrow(() -> carRepo.getCars(1000L, 999L));
        assertEquals(Collections.singletonList(car), cars);
    }

    @Test
    void getCars_driverNotFound() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.getCars(1000, 999));
        assertEquals(new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND), serviceError);
    }

    @Test
    void getCars_userDoesnMatch() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.getCars(100, 999));
        assertEquals(HttpErrorMessage.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void removeCar() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(carEntity)).when(carJpaRepository).findById(uuid);
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(carEntity.getDriverId());
        assertDoesNotThrow(() -> carRepo.removeCar(1000, uuid));
        Mockito.verify(carJpaRepository).delete(carEntity);
    }

    @Test
    void removeCar_carNotFound() {
        Mockito.doReturn(Optional.empty()).when(carJpaRepository).findById(uuid);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCar(1000, uuid));
        assertEquals(new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.CAR_ID_NOT_FOUND), serviceError);
    }

    @Test
    void removeCar_userIdDoesntMatch() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(carEntity)).when(carJpaRepository).findById(uuid);
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(carEntity.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCar(100L, uuid));
        assertEquals(HttpErrorMessage.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void removeCarsWithDriverId() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(999L);
        assertDoesNotThrow(() -> carRepo.removeCarsWithDriverId(1000, 999));
        Mockito.verify(carJpaRepository).deleteByDriverId(999L);
    }

    @Test
    void removeCarsWithDriverId_driverIdNotFound() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCarsWithDriverId(1000, 999));
        assertEquals(new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND), serviceError);
    }

    @Test
    void removeCarsWithDriverId_userIdDoesntMatch() {
        Mockito.doReturn(Optional.of(driverWithId(100L))).when(driverJpaRepository).findById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCarsWithDriverId(1000, 999));
        assertEquals(HttpErrorMessage.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void addCar_driverIdNotFound() {
        CarRequest car = carFakeProvider.generateCarRequest();
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findById(car.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.addCar(1000, car));
        assertEquals(new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND), serviceError);
    }

    @Test
    void addCar_userIdDoesntMatch() {
        CarRequest car = carFakeProvider.generateCarRequest();
        Mockito.doReturn(Optional.of(driverWithId(100L))).when(driverJpaRepository).findById(car.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.addCar(1000, car));
        assertEquals(new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.NOT_AUTHORISED), serviceError);
    }

    @Test
    void addCar() throws ServiceError {
        CarRequest car = carFakeProvider.generateCarRequest();
        CarEntity carEntity = new CarEntity(null, car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(),
                car.getDriverId());
        Mockito.doReturn(carEntity).when(carJpaRepository).save(carEntity);
        Mockito.doReturn(Optional.of(driverWithId(1000L))).when(driverJpaRepository).findById(car.getDriverId());
        assertDoesNotThrow(() -> carRepo.addCar(1000L, car));
        Mockito.verify(carJpaRepository).save(carEntity);
    }

    private DriverEntity driverWithId(long id) {
        return new DriverEntity("test", "test", id);
    }
}