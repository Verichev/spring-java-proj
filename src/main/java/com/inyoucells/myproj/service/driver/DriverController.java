package com.inyoucells.myproj.service.driver;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.response.AddDriverResponse;
import com.inyoucells.myproj.models.response.DriverDetailResponse;
import com.inyoucells.myproj.models.response.DriverResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/driver")
    ResponseEntity<DriverResponse> getDrivers(@RequestAttribute Long userId,
                                              @RequestParam(defaultValue = "0", required = false) Integer page,
                                              @RequestParam(defaultValue = "5", required = false) Integer size) {
        return withResponse(new DriverResponse(driverService.getDrivers(userId, page, size)));
    }

    @GetMapping("/driver/full")
    ResponseEntity<DriverDetailResponse> getDriversFull(@RequestAttribute Long userId,
                                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                                        @RequestParam(defaultValue = "5", required = false) Integer size) {
        return withResponse(new DriverDetailResponse(driverService.getDriversFull(userId, page, size)));
    }

    @DeleteMapping("/driver/{driverId}")
    public void removeDriver(@RequestAttribute Long userId, @PathVariable long driverId) {
        driverService.removeDriver(userId, driverId);
    }

    @PostMapping("/driver")
    ResponseEntity<AddDriverResponse> addDriver(@RequestAttribute Long userId, @RequestBody Driver driver) {
        return withResponse(new AddDriverResponse(driverService.addDriver(userId, driver)));
    }
}
