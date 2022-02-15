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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @ApiOperation(value = "Get drivers", notes = "Get list of drivers with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number", required = false, paramType = "Integer"),
            @ApiImplicitParam(name = "size", value = "Size of the page", required = false, paramType = "Integer")
    })
    @GetMapping("/driver")
    ResponseEntity<DriverResponse> getDrivers(@ApiParam(hidden = true) @RequestAttribute Long userId,
                                              @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "5", required = false) int size) {
        return withResponse(new DriverResponse(driverService.getDrivers(userId, page, size)));
    }

    @ApiOperation(value = "Get drivers with cars", notes = "Get list of drivers with car details with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number", required = false, paramType = "Integer"),
            @ApiImplicitParam(name = "size", value = "Size of the page", required = false, paramType = "Integer")
    })
    @GetMapping("/driver/full")
    ResponseEntity<DriverDetailResponse> getDriversFull(@ApiParam(hidden = true) @RequestAttribute Long userId,
                                                        @RequestParam(defaultValue = "0", required = false) int page,
                                                        @RequestParam(defaultValue = "5", required = false) int size) {
        return withResponse(new DriverDetailResponse(driverService.getDriversFull(userId, page, size)));
    }

    @ApiOperation(value = "Remove driver", notes = "Remove driver by id")
    @ApiImplicitParam(name = "driverId", value = "Driver id", required = true, dataTypeClass = Long.class, paramType = "path")
    @DeleteMapping("/driver/{driverId}")
    public void removeDriver(@ApiParam(hidden = true) @RequestAttribute Long userId, @PathVariable long driverId) {
        driverService.removeDriver(userId, driverId);
    }

    @ApiOperation(value = "Add driver", notes = "Add new driver with details")
    @ApiImplicitParam(name = "driver", value = "Driver details", required = true, dataTypeClass = DriverRequest.class)
    @PostMapping("/driver")
    ResponseEntity<AddDriverResponse> addDriver(@ApiParam(hidden = true) @RequestAttribute Long userId, @RequestBody DriverRequest driver) {
        return withResponse(new AddDriverResponse(driverService.addDriver(userId, driver)));
    }
}
