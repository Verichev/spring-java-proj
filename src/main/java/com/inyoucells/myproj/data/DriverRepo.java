package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.DriverEntity;
import com.inyoucells.myproj.data.jpa.DriverJpaRepository;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.DriverStripped;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
                .map(this::mapToDriver)
                .collect(Collectors.toList());
    }

    private Driver mapToDriver(DriverEntity driverEntity) {
        List<Car> cars = driverEntity.getCars().stream().map(Car::new).collect(Collectors.toList());
        return new Driver(driverEntity.getId(), driverEntity.getName(), driverEntity.getLicence(), cars);
    }

    public List<DriverStripped> getDrivers(long userId) {
        return driverJpaRepository
                .findAllByUserId(userId)
                .stream()
                .map(DriverStripped::new)
                .collect(Collectors.toList());
    }

    public void removeDriver(long userId, long driverId) throws ServiceError {
        Optional<DriverEntity> driver = driverJpaRepository.findById(driverId);
        if (driver.isEmpty()) {
            throw new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND);
        } else if (driver.get().getUserId() != userId) {
            throw new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.NOT_AUTHORISED);
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
