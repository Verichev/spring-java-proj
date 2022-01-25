package com.inyoucells.myproj.service.driver;

import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Driver;
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
        return driverRepo.getDrivers();
    }

    @Override
    public void removeDriver(long userId, long id) {
        driverRepo.removeDriver(id);
    }

    @Override
    public long addDriver(long userId, Driver driver) {
        return driverRepo.addDriver(driver);
    }
}
