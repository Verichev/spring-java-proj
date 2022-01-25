package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    private final CarService carService;
    private final ControllerUtils controllerUtils;

    @Autowired
    public CarController(CarService carService, ControllerUtils controllerUtils) {
        this.carService = carService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("/car")
    ResponseEntity<Object> getCars(@RequestHeader String token) {
        return controllerUtils.authorizedFunction(token, carService::getCars);
    }

    @DeleteMapping("/car/{id}")
    ResponseEntity<Object> removeCar(@RequestHeader String token, @PathVariable long carId) {
        return controllerUtils.authorizedConsumer(token, userId -> carService.removeCar(userId, carId));
    }

    @PostMapping("/car")
    ResponseEntity<Object> addCar(@RequestHeader String token, @RequestBody Car car) {
        return controllerUtils.rawAuthorizedFunction(token, userId -> {
            if (car.getDriverId() == null) {
                return new ResponseEntity<>(
                        HttpError.MISSING_DRIVER_ID, HttpStatus.BAD_REQUEST);
            } else {
                long id = carService.addCar(userId, car);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
        });
    }
}
