package com.inyoucells.myproj.service.car;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.inyoucells.myproj.data.CarFakeProvider;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.car.data.repo.CarRepo;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.car.models.CarRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepo carRepo;
    private CarServiceImpl carService;
    private final CarFakeProvider carFakeProvider = new CarFakeProvider(0);

    @BeforeEach
    void setup() {
        carService = new CarServiceImpl(carRepo);
    }

    @Test
    void getCars() throws ServiceError {
        Mockito.doReturn(Collections.emptyList()).when(carRepo).getCars(10L, 100L);
        List<Car> result = carService.getCars(10L, 100L);

        Mockito.verify(carRepo).getCars(10L, 100L);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void removeCar() throws ServiceError {
        UUID uuid = UUID.randomUUID();
        carService.removeCar(10, uuid);

        Mockito.verify(carRepo).removeCar(10, uuid);
    }

    @Test
    void addCar() throws ServiceError {
        CarRequest car = carFakeProvider.generateCarRequest();
        carService.addCar(10, car);

        Mockito.verify(carRepo).addCar(10, car);
    }
}