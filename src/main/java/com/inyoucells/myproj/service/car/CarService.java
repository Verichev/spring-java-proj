package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import org.springframework.http.ResponseEntity;

public interface CarService {
    ResponseEntity<Object> getCars(String token);

    ResponseEntity<Object> removeCar(String token, long id);

    ResponseEntity<Object> addCar(String token, Car car);
}
