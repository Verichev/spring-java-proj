package com.inyoucells.myproj.service.driver.data.repo;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;
import com.inyoucells.myproj.service.car.models.Car;
import com.inyoucells.myproj.service.driver.data.DriverEntity;
import com.inyoucells.myproj.service.driver.models.Driver;
import com.inyoucells.myproj.service.driver.models.DriverDetail;
import com.inyoucells.myproj.service.driver.models.DriverRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<DriverDetail> getDriversFull(long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return driverJpaRepository
                .findAllByUserId(userId, pageable)
                .stream()
                .map(this::mapToDriver)
                .collect(Collectors.toList());
    }

    private DriverDetail mapToDriver(DriverEntity driverEntity) {
        List<Car> cars = driverEntity.getCars().stream().map(Car::new).collect(Collectors.toList());
        return new DriverDetail(driverEntity.getId(), driverEntity.getName(), driverEntity.getLicence(), cars);
    }

    public List<Driver> getDrivers(long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return driverJpaRepository
                .findAllByUserId(userId, pageable)
                .stream()
                .map(Driver::new)
                .collect(Collectors.toList());
    }

    public void removeDriver(long userId, long driverId) throws ServiceError {
        Optional<DriverEntity> driver = driverJpaRepository.findById(driverId);
        if (driver.isEmpty()) {
            throw new ServiceError(TypicalError.DRIVER_ID_NOT_FOUND);
        } else if (driver.get().getUserId() != userId) {
            throw new ServiceError(TypicalError.NOT_AUTHORISED);
        }
        driverJpaRepository.deleteById(driverId);
    }

    public long addDriver(long userId, DriverRequest driver) {
        DriverEntity result = driverJpaRepository.save(new DriverEntity(driver.getName(), driver.getLicence(), userId));
        return result.getId();
    }

    public void clean() {
        driverJpaRepository.deleteAll();
    }
}
