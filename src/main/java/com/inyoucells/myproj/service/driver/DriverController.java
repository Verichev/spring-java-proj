package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping("/driver")
    ResponseEntity<Object> getDrivers(@RequestHeader("token") String token) {
        return driverService.getDrivers(token);
    }

    @DeleteMapping("/driver/{id}")
    ResponseEntity<Object> removeDriver(@RequestHeader("token") String token, @PathVariable long id) {
        return driverService.removeDriver(token, id);
    }

    @PostMapping("/driver")
    ResponseEntity<Object> addDriver(@RequestHeader("token") String token, @RequestBody Driver driver) {
        return driverService.addDriver(token, driver);
    }
}
