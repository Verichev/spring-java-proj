package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.driver.data.repo.DriverRepo;
import com.inyoucells.myproj.service.driver.models.Driver;
import com.inyoucells.myproj.service.driver.models.DriverDetail;
import com.inyoucells.myproj.service.driver.models.DriverRequest;

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
    public List<Driver> getDrivers(long userId, Integer page, Integer size) {
        return driverRepo.getDrivers(userId, page, size);
    }

    @Override
    public List<DriverDetail> getDriversFull(long userId, Integer page, Integer size) {
        return driverRepo.getDriversFull(userId, page, size);
    }

    @Override
    public void removeDriver(long userId, long driverId) throws ServiceError {
        driverRepo.removeDriver(userId, driverId);
    }

    @Override
    public long addDriver(long userId, DriverRequest driver) {
        return driverRepo.addDriver(userId, driver);
    }
}
