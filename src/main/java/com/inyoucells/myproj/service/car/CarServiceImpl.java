package com.inyoucells.myproj.service.car;

import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.models.ApiError;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.TokenValidationResult;
import com.inyoucells.myproj.service.auth.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepo carRepo;

    @Autowired
    TokenValidator tokenValidator;

    @Override
    public ResponseEntity<Object> getCars(String token) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        }
        return new ResponseEntity<>(
                carRepo.getCars(), new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeCar(String token, long id) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        }
        carRepo.removeCar(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCar(String token, Car car) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        } else if (car.getDriverId() == null) {
            return new ResponseEntity<>(
                    new ApiError(HttpStatus.BAD_REQUEST, "Driver id should be provided"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(carRepo.addCar(car), new HttpHeaders(), HttpStatus.OK);
    }
}
