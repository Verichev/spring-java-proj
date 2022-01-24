package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;
import org.springframework.http.ResponseEntity;

public interface DriverService {
    ResponseEntity<Object> getDrivers(String token);

    ResponseEntity<Object> removeDriver(String token, long id);

    ResponseEntity<Object> addDriver(String token, Driver driver);
}
