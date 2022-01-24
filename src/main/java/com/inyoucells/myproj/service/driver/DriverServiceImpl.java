package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.TokenValidationResult;
import com.inyoucells.myproj.service.auth.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    TokenValidator tokenValidator;

    @Override
    public ResponseEntity<Object> getDrivers(String token) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        }
        return new ResponseEntity<>(
                driverRepo.getDrivers(), new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeDriver(String token, long id) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        }
        driverRepo.removeDriver(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addDriver(String token, Driver driver) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != null) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), new HttpHeaders(), validationResult.getApiError().getStatus());
        }
        return new ResponseEntity<>(
                driverRepo.addDriver(driver), new HttpHeaders(), HttpStatus.OK);
    }
}
