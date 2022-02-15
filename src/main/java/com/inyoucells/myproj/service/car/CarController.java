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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ApiOperation(value = "Get all cars", notes = "Get all cars for specific driver")
    @GetMapping("/car")
    ResponseEntity<CarResponse> getCars(@ApiParam(hidden = true) @RequestAttribute Long userId, long driverId) {
        return withResponse(new CarResponse(carService.getCars(userId, driverId)));
    }

    @ApiOperation(value = "Remove car", notes = "Remove car by carId")
    @DeleteMapping("/car/{carId}")
    void removeCar(@ApiParam(hidden = true) @RequestAttribute Long userId, @PathVariable UUID carId) {
        carService.removeCar(userId, carId);
    }

    @ApiOperation(value = "Create new car", notes = "Create car by given car information")
    @PostMapping("/car")
    ResponseEntity<AddCarResponse> addCar(@ApiParam(hidden = true) @RequestAttribute Long userId, @RequestBody CarRequest car) {
        if (car.getDriverId() == null) {
            throw new ServiceError(TypicalError.MISSING_DRIVER_ID);
        } else {
            UUID id = carService.addCar(userId, car);
            return withResponse(new AddCarResponse(id));
        }
    }

    @ApiOperation(value = "Search cars by brand", notes = "Search cars by keyword as part of it's brand")
    @GetMapping("/car/search/brand")
    ResponseEntity<CarResponse> searchCarsByBrand(@ApiParam(hidden = true) @RequestAttribute Long userId, String keyword) {
        return withResponse(new CarResponse(carService.searchByBrand(userId, keyword)));
    }

    @ApiOperation(value = "Get cars by year and brand", notes = "Get cars by year and brand provided")
    @GetMapping("/car/yearbrand")
    ResponseEntity<CarResponse> getCarsByYearAndBrand(@ApiParam(hidden = true) @RequestAttribute Long userId, String year, String brand) {
        return withResponse(new CarResponse(carService.getCarsByYearAndBrand(userId, year, brand)));
    }

    @ApiOperation(value = "Get cars with horsepower", notes = "Get cars with horsepower above certain value")
    @GetMapping("/car/morehorsepower")
    ResponseEntity<CarResponse> getCarsWitHorsepowerMore(@ApiParam(hidden = true) @RequestAttribute Long userId, Integer minHorsePower) {
        return withResponse(new CarResponse(carService.getCarsWitHorsepowerMore(userId, minHorsePower)));
    }
}
