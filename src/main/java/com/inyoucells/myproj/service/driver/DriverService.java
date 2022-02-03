package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.DriverDetail;
import com.inyoucells.myproj.models.errors.ServiceError;

import java.util.List;

public interface DriverService {
    List<Driver> getDrivers(long userId, Integer page, Integer size) throws ServiceError;

    List<DriverDetail> getDriversFull(long userId, Integer page, Integer size) throws ServiceError;

    void removeDriver(long userId, long id) throws ServiceError;

    long addDriver(long userId, Driver driver) throws ServiceError;
}
