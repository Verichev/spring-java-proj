package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.response.AddDriverResponse;
import com.inyoucells.myproj.models.response.DriverDetailResponse;
import com.inyoucells.myproj.models.response.DriverResponse;
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
    ResponseEntity<ControllerResponse> getDrivers(@RequestHeader String token,
                                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                                  @RequestParam(defaultValue = "5", required = false) Integer size) {
        return controllerUtils.authorizedFunction(token, userId -> new DriverResponse(driverService.getDrivers(userId, page, size)));
    }

    @GetMapping("/driver/full")
    ResponseEntity<ControllerResponse> getDriversFull(@RequestHeader String token,
                                                      @RequestParam(defaultValue = "0", required = false) Integer page,
                                                      @RequestParam(defaultValue = "5", required = false) Integer size) {
        return controllerUtils.authorizedFunction(token, userId -> new DriverDetailResponse(driverService.getDriversFull(userId, page, size)));
    }

    @DeleteMapping("/driver/{driverId}")
    public ResponseEntity<ControllerResponse> removeDriver(@RequestHeader String token, @PathVariable long driverId) {
        return controllerUtils.authorizedConsumer(token, userId -> driverService.removeDriver(userId, driverId));
    }

    @PostMapping("/driver")
    ResponseEntity<ControllerResponse> addDriver(@RequestHeader String token, @RequestBody Driver driver) {
        return controllerUtils.authorizedFunction(token, userId -> new AddDriverResponse(driverService.addDriver(userId, driver)));
    }
}
