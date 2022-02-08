package com.inyoucells.myproj.service.car;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;
import com.inyoucells.myproj.models.response.AddCarResponse;
import com.inyoucells.myproj.models.response.CarResponse;

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

@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    ResponseEntity<CarResponse> getCars(@RequestAttribute Long userId, long driverId) {
        return withResponse(new CarResponse(carService.getCars(userId, driverId)));
    }

    @DeleteMapping("/car/{carId}")
    void removeCar(@RequestAttribute Long userId, @PathVariable UUID carId) {
        carService.removeCar(userId, carId);
    }

    @PostMapping("/car")
    ResponseEntity<AddCarResponse> addCar(@RequestAttribute Long userId, @RequestBody Car car) {
        if (car.getDriverId() == null) {
            throw new ServiceError(TypicalError.MISSING_DRIVER_ID);
        } else {
            UUID id = carService.addCar(userId, car);
            return withResponse(new AddCarResponse(id));
        }
    }

    @GetMapping("/car/search/brand")
    ResponseEntity<CarResponse> searchCarsByBrand(@RequestAttribute Long userId, String keyword) {
        return withResponse(new CarResponse(carService.searchByBrand(userId, keyword)));
    }

    @GetMapping("/car/yearbrand")
    ResponseEntity<CarResponse> getCarsByYearAndBrand(@RequestAttribute Long userId, String year, String brand) {
        return withResponse(new CarResponse(carService.getCarsByYearAndBrand(userId, year, brand)));
    }

    @GetMapping("/car/morehorsepower")
    ResponseEntity<CarResponse> getCarsWitHorsepowerMore(@RequestAttribute Long userId, Integer minHorsePower) {
        return withResponse(new CarResponse(carService.getCarsWitHorsepowerMore(userId, minHorsePower)));
    }
}
