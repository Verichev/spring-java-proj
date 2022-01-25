package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.Driver;

import java.util.List;

public interface DriverService {
    List<Driver> getDrivers(long userId);

    void removeDriver(long userId, long id);

    long addDriver(long userId, Driver driver);
}
