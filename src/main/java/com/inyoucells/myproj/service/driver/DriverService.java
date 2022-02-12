package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.driver.models.Driver;
import com.inyoucells.myproj.service.driver.models.DriverDetail;
import com.inyoucells.myproj.service.driver.models.DriverRequest;

import java.util.List;

public interface DriverService {
    List<Driver> getDrivers(long userId, Integer page, Integer size) throws ServiceError;

    List<DriverDetail> getDriversFull(long userId, Integer page, Integer size) throws ServiceError;

    void removeDriver(long userId, long id) throws ServiceError;

    long addDriver(long userId, DriverRequest driver) throws ServiceError;
}
