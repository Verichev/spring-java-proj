package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.DriverEntity;
import com.inyoucells.myproj.data.jpa.DriverJpaRepository;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.models.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverRepo {

    private final DriverJpaRepository driverJpaRepository;

    @Autowired
    public DriverRepo(DriverJpaRepository driverJpaRepository) {
        this.driverJpaRepository = driverJpaRepository;
    }

    @Transactional
    public List<Driver> getDriversFull(long userId) {
        return driverJpaRepository
                .findAllByUserId(userId)
                .stream()
                .map(driverEntity -> new Driver(driverEntity, true))
                .collect(Collectors.toList());
    }

    public List<Driver> getDrivers(long userId) {
        return driverJpaRepository
                .findAllByUserId(userId)
                .stream()
                .map(driverEntity -> new Driver(driverEntity, false))
                .collect(Collectors.toList());
    }

    public void removeDriver(long userId, long driverId) throws ServiceError {
        Optional<DriverEntity> driver = driverJpaRepository.findById(driverId);
        if (driver.isEmpty()) {
            throw new ServiceError(HttpError.BAD_REQUEST);
        } else if (driver.get().getUserId() != userId) {
            throw new ServiceError(HttpError.NOT_AUTHORISED);
        }
        driverJpaRepository.deleteById(driverId);
    }

    public long addDriver(long userId, Driver driver) {
        DriverEntity result = driverJpaRepository.save(new DriverEntity(driver.getName(), driver.getLicence(), userId));
        return result.getId();
    }

    public void clean() {
        driverJpaRepository.deleteAll();
    }
}
