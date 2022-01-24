package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/car")
    ResponseEntity<Object> getCars(@RequestHeader("token") String token) {
        return carService.getCars(token);
    }

    @DeleteMapping("/car/{id}")
    ResponseEntity<Object> removeCar(@RequestHeader("token") String token, @PathVariable long id) {
        return carService.removeCar(token, id);
    }

    @PostMapping("/car")
    ResponseEntity<Object> addCar(@RequestHeader("token") String token, @RequestBody Car car) {
        return carService.addCar(token, car);
    }
}
