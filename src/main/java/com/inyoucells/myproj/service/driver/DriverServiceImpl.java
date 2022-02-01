package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;

    @Autowired
    public DriverServiceImpl(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public List<Driver> getDrivers(long userId) {
        return driverRepo.getDrivers(userId);
    }

    @Override
    public List<Driver> getDriversFull(long userId) {
        return driverRepo.getDriversFull(userId);
    }

    @Override
    public void removeDriver(long userId, long driverId) throws ServiceError {
        driverRepo.removeDriver(userId, driverId);
    }

    @Override
    public long addDriver(long userId, Driver driver) {
        return driverRepo.addDriver(userId, driver);
    }
}
