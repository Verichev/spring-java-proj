package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.CarEntity;
import com.inyoucells.myproj.data.jpa.CarJpaRepository;
import com.inyoucells.myproj.data.jpa.DriverJpaRepository;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.models.ServiceError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepoTest {

    @Mock
    private CarJpaRepository carJpaRepository;
    @Mock
    private DriverJpaRepository driverJpaRepository;

    private CarRepo carRepo;
    private CarFakeProvider carFakeProvider;

    @BeforeEach
    void setup() {
        carRepo = new CarRepo(carJpaRepository, driverJpaRepository);
        carFakeProvider = new CarFakeProvider(0);
    }

    @Test
    void getCars() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Collections.singletonList(carEntity)).when(carJpaRepository).findAllByDriverId(999L);
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(999L);
        List<Car> cars = assertDoesNotThrow(() -> carRepo.getCars(1000L, 999L));
        assertEquals(Collections.singletonList(car), cars);
    }

    @Test
    void getCars_driverNotFound() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findUserIdById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.getCars(1000, 999));
        assertEquals(HttpError.BAD_REQUEST, serviceError.getHttpError());
    }

    @Test
    void getCars_userDoesnMatch() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.getCars(100, 999));
        assertEquals(HttpError.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void removeCar() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(carEntity)).when(carJpaRepository).findById(999L);
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(carEntity.getDriverId());
        assertDoesNotThrow(() -> carRepo.removeCar(1000, 999));
        Mockito.verify(carJpaRepository).delete(carEntity);
    }

    @Test
    void removeCar_carNotFound() {
        Mockito.doReturn(Optional.empty()).when(carJpaRepository).findById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCar(1000, 999));
        assertEquals(HttpError.BAD_REQUEST, serviceError.getHttpError());
    }

    @Test
    void removeCar_userIdDoesntMatch() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(carEntity)).when(carJpaRepository).findById(999L);
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(carEntity.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCar(100L, 999L));
        assertEquals(HttpError.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void removeCarsWithDriverId() {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(999L);
        assertDoesNotThrow(() -> carRepo.removeCarsWithDriverId(1000, 999));
        Mockito.verify(carJpaRepository).deleteByDriverId(999L);
    }

    @Test
    void removeCarsWithDriverId_driverIdNotFound() {
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findUserIdById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCarsWithDriverId(1000, 999));
        assertEquals(HttpError.BAD_REQUEST, serviceError.getHttpError());
    }

    @Test
    void removeCarsWithDriverId_userIdDoesntMatch() {
        Mockito.doReturn(Optional.of(100L)).when(driverJpaRepository).findUserIdById(999L);
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.removeCarsWithDriverId(1000, 999));
        assertEquals(HttpError.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void addCar_driverIdNotFound() {
        Car car = carFakeProvider.generateCar();
        Mockito.doReturn(Optional.empty()).when(driverJpaRepository).findUserIdById(car.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.addCar(1000, car));
        assertEquals(HttpError.BAD_REQUEST, serviceError.getHttpError());
    }

    @Test
    void addCar_userIdDoesntMatch() {
        Car car = carFakeProvider.generateCar();
        Mockito.doReturn(Optional.of(100L)).when(driverJpaRepository).findUserIdById(car.getDriverId());
        ServiceError serviceError = assertThrows(ServiceError.class, () -> carRepo.addCar(1000, car));
        assertEquals(HttpError.NOT_AUTHORISED, serviceError.getHttpError());
    }

    @Test
    void addCar() throws ServiceError {
        Car car = carFakeProvider.generateCar();
        CarEntity carEntity = new CarEntity(car.getId(), car.getBrand(), car.getYear(), car.isUsed(), car.getHorsepower(), car.getDriverId());
        Mockito.doReturn(carEntity).when(carJpaRepository).save(carEntity);
        Mockito.doReturn(Optional.of(1000L)).when(driverJpaRepository).findUserIdById(car.getDriverId());
        Long id = assertDoesNotThrow(() -> carRepo.addCar(1000L, car));
        Mockito.verify(carJpaRepository).save(carEntity);
    }
}