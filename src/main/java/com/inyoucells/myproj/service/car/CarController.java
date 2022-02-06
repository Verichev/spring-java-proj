package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.response.AddCarResponse;
import com.inyoucells.myproj.models.response.CarResponse;
import com.inyoucells.myproj.utils.ControllerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    ResponseEntity<CarResponse> getCars(@RequestHeader String token, long driverId) {
        return controllerUtils.authorizedFunction(token,
                userId -> new CarResponse(carService.getCars(userId, driverId)));
    }

    @DeleteMapping("/car/{carId}")
    ResponseEntity<ResponseEntity<Void>> removeCar(@RequestHeader String token, @PathVariable UUID carId) {
        return controllerUtils.authorizedConsumer(token, userId -> carService.removeCar(userId, carId));
    }

    @PostMapping("/car")
    ResponseEntity<AddCarResponse> addCar(@RequestHeader String token, @RequestBody Car car) {
        return controllerUtils.authorizedFunction(token, userId -> {
            if (car.getDriverId() == null) {
                throw new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.MISSING_DRIVER_ID);
            } else {
                UUID id = carService.addCar(userId, car);
                return new AddCarResponse(id);
            }
        });
    }

    @GetMapping("/car/search/brand")
    ResponseEntity<CarResponse> searchCarsByBrand(@RequestHeader String token, String keyword) {
        return controllerUtils.authorizedFunction(token,
                userId -> new CarResponse(carService.searchByBrand(userId, keyword)));
    }

    @GetMapping("/car/yearbrand")
    ResponseEntity<CarResponse> getCarsByYearAndBrand(@RequestHeader String token, String year, String brand) {
        return controllerUtils.authorizedFunction(token,
                userId -> new CarResponse(carService.getCarsByYearAndBrand(userId, year, brand)));
    }

    @GetMapping("/car/morehorsepower")
    ResponseEntity<CarResponse> getCarsWitHorsepowerMore(@RequestHeader String token, Integer minHorsePower) {
        return controllerUtils.authorizedFunction(token,
                userId -> new CarResponse(carService.getCarsWitHorsepowerMore(userId, minHorsePower)));
    }
}
