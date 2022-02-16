package com.inyoucells.myproj.service.driver;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.service.car.models.AddDriverResponse;
import com.inyoucells.myproj.service.driver.models.DriverDetailResponse;
import com.inyoucells.myproj.service.driver.models.DriverRequest;
import com.inyoucells.myproj.service.driver.models.DriverResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Api(value = "driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @Operation(summary = "Get list of drivers with pagination")
    @GetMapping("/driver")
    ResponseEntity<DriverResponse> getDrivers(@Parameter(hidden = true) @RequestAttribute Long userId,
                                              @RequestParam(name = "Page number", defaultValue = "0", required = false) int page,
                                              @RequestParam(name = "Size of the page", defaultValue = "5", required = false) int size) {
        return withResponse(new DriverResponse(driverService.getDrivers(userId, page, size)));
    }

    @Operation(summary = "Get list of drivers with car details with pagination")
    @GetMapping("/driver/full")
    ResponseEntity<DriverDetailResponse> getDriversFull(@Parameter(hidden = true) @RequestAttribute Long userId,
                                                        @RequestParam(name = "Page number", defaultValue = "0", required = false) int page,
                                                        @RequestParam(name = "Size of the page", defaultValue = "5", required = false) int size) {
        return withResponse(new DriverDetailResponse(driverService.getDriversFull(userId, page, size)));
    }


    @Operation(summary = "Remove driver by id")
    @DeleteMapping("/driver/{driverId}")
    public void removeDriver(@Parameter(hidden = true) @RequestAttribute Long userId, @Parameter(name = "Driver id") @PathVariable long driverId) {
        driverService.removeDriver(userId, driverId);
    }

    @Operation(summary = "Add new driver with details")
    @PostMapping("/driver")
    ResponseEntity<AddDriverResponse> addDriver(@Parameter(hidden = true) @RequestAttribute Long userId, @Parameter(name = "Driver details") @RequestBody DriverRequest driver) {
        return withResponse(new AddDriverResponse(driverService.addDriver(userId, driver)));
    }
}
