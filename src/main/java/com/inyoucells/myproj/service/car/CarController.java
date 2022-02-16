package com.inyoucells.myproj.service.car;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;
import com.inyoucells.myproj.service.car.models.AddCarResponse;
import com.inyoucells.myproj.service.car.models.CarRequest;
import com.inyoucells.myproj.service.car.models.CarResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Api(value = "car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Operation(summary = "Get all cars for specific driver")
    @GetMapping("/car")
    ResponseEntity<CarResponse> getCars(@Parameter(hidden = true) @RequestAttribute Long userId, long driverId) {
        return withResponse(new CarResponse(carService.getCars(userId, driverId)));
    }

    @Operation(summary = "Remove car by carId")
    @DeleteMapping("/car/{carId}")
    void removeCar(@Parameter(hidden = true) @RequestAttribute Long userId, @PathVariable UUID carId) {
        carService.removeCar(userId, carId);
    }

    @Operation(summary = "Create car by given car information")
    @PostMapping("/car")
    ResponseEntity<AddCarResponse> addCar(@Parameter(hidden = true) @RequestAttribute Long userId, @RequestBody CarRequest car) {
        if (car.getDriverId() == null) {
            throw new ServiceError(TypicalError.MISSING_DRIVER_ID);
        } else {
            UUID id = carService.addCar(userId, car);
            return withResponse(new AddCarResponse(id));
        }
    }

    @Operation(summary = "Search cars by keyword as part of it's brand")
    @GetMapping("/car/search/brand")
    ResponseEntity<CarResponse> searchCarsByBrand(@Parameter(hidden = true) @RequestAttribute Long userId, String keyword) {
        return withResponse(new CarResponse(carService.searchByBrand(userId, keyword)));
    }

    @Operation(summary = "Get cars by year and brand provided")
    @GetMapping("/car/yearbrand")
    ResponseEntity<CarResponse> getCarsByYearAndBrand(@Parameter(hidden = true) @RequestAttribute Long userId, String year, String brand) {
        return withResponse(new CarResponse(carService.getCarsByYearAndBrand(userId, year, brand)));
    }

    @Operation(summary = "Get cars with horsepower above certain value")
    @GetMapping("/car/morehorsepower")
    ResponseEntity<CarResponse> getCarsWitHorsepowerMore(@Parameter(hidden = true) @RequestAttribute Long userId, Integer minHorsePower) {
        return withResponse(new CarResponse(carService.getCarsWitHorsepowerMore(userId, minHorsePower)));
    }
}
