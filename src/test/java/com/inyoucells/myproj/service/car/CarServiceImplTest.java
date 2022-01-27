package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.data.CarFakeProvider;
import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.models.Car;
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
    void getCars() {
        Mockito.doReturn(Collections.emptyList()).when(carRepo).getCars();
        List<Car> result = carService.getCars(-1);

        Mockito.verify(carRepo).getCars();
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void removeCar() {
        carService.removeCar(-1, 3);

        Mockito.verify(carRepo).removeCar(3);
    }

    @Test
    void addCar() {
        Car car = carFakeProvider.generateCar();
        carService.addCar(-1, car);

        Mockito.verify(carRepo).addCar(car);
    }
}