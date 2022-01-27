package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverController {

    private final DriverService driverService;
    private final ControllerUtils controllerUtils;

    public DriverController(DriverService driverService, ControllerUtils controllerUtils) {
        this.driverService = driverService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("/driver")
    ResponseEntity<Object> getDrivers(@RequestHeader String token) {
        return controllerUtils.authorizedFunction(token, driverService::getDrivers);
    }

    @DeleteMapping("/driver/{driverId}")
    ResponseEntity<Object> removeDriver(@RequestHeader String token, @PathVariable long driverId) {
        return controllerUtils.authorizedConsumer(token, userId -> driverService.removeDriver(userId, driverId));
    }

    @PostMapping("/driver")
    ResponseEntity<Object> addDriver(@RequestHeader String token, @RequestBody Driver driver) {
        return controllerUtils.authorizedFunction(token, userId -> driverService.addDriver(userId, driver));
    }
}
