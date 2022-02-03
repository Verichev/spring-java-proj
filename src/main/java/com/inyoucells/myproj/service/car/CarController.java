package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.inyoucells.myproj.utils.ResponseUtils.*;

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
    ResponseEntity<ControllerResponse> getCars(@RequestHeader String token, long driverId) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(carService.getCars(userId, driverId)));
    }

    @DeleteMapping("/car/{carId}")
    ResponseEntity<ControllerResponse> removeCar(@RequestHeader String token, @PathVariable long carId) {
        return controllerUtils.authorizedConsumer(token, userId -> carService.removeCar(userId, carId));
    }

    @PostMapping("/car")
    ResponseEntity<ControllerResponse> addCar(@RequestHeader String token, @RequestBody Car car) {
        return controllerUtils.rawAuthorizedFunction(token, userId -> {
            if (car.getDriverId() == null) {
                return withError(HttpStatus.BAD_REQUEST, HttpErrorMessage.MISSING_DRIVER_ID);
            } else {
                UUID id = carService.addCar(userId, car);
                return withPayload(id);
            }
        });
    }

    @GetMapping("/car/search/brand")
    ResponseEntity<ControllerResponse> searchCarsByBrand(@RequestHeader String token, String keyword) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(carService.searchByBrand(userId, keyword)));
    }

    @GetMapping("/car/yearbrand")
    ResponseEntity<ControllerResponse> getCarsByYearAndBrand(@RequestHeader String token, String year, String brand) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(carService.getCarsByYearAndBrand(userId, year, brand)));
    }

    @GetMapping("/car/morehorsepower")
    ResponseEntity<ControllerResponse> getCarsWitHorsepowerMore(@RequestHeader String token, Integer minHorsePower) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(carService.getCarsWitHorsepowerMore(userId, minHorsePower)));
    }
}
