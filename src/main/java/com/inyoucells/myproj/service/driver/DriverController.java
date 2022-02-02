package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inyoucells.myproj.utils.ResponseUtils.basicPayload;

@RestController
public class DriverController {

    private final DriverService driverService;
    private final ControllerUtils controllerUtils;

    public DriverController(DriverService driverService, ControllerUtils controllerUtils) {
        this.driverService = driverService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("/driver")
    ResponseEntity<ControllerResponse> getDrivers(@RequestHeader String token) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(driverService.getDrivers(userId)));
    }

    @GetMapping("/driver/full")
    ResponseEntity<ControllerResponse> getDriversFull(@RequestHeader String token) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(driverService.getDriversFull(userId)));
    }

    @DeleteMapping("/driver/{driverId}")
    ResponseEntity<ControllerResponse> removeDriver(@RequestHeader String token, @PathVariable long driverId) {
        return controllerUtils.authorizedConsumer(token, userId -> driverService.removeDriver(userId, driverId));
    }

    @PostMapping("/driver")
    ResponseEntity<ControllerResponse> addDriver(@RequestHeader String token, @RequestBody Driver driver) {
        return controllerUtils.authorizedFunction(token, userId -> basicPayload(driverService.addDriver(userId, driver)));
    }
}
