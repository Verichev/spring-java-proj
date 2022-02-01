package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.ServiceError;

import java.util.List;

public interface DriverService {
    List<Driver> getDrivers(long userId) throws ServiceError;

    List<Driver> getDriversFull(long userId) throws ServiceError;

    void removeDriver(long userId, long id) throws ServiceError;

    long addDriver(long userId, Driver driver) throws ServiceError;
}
