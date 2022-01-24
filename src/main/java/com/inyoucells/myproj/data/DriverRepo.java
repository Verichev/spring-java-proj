package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverRepo {

    @Autowired
    CarRepo carRepo;

    private List<Driver> drivers = new ArrayList<>();
    private int idCounter = 0;

    public List<Driver> getDrivers() {
        return drivers;
    }

    public synchronized void removeDriver(long id) {
        drivers = drivers.stream().filter(driver -> driver.getId() != id).collect(Collectors.toList());
        carRepo.removeCarsWithDriverIds(id);
    }

    public synchronized int addDriver(Driver driver) {
        idCounter++;
        drivers.add(new Driver(idCounter, driver.getName(), driver.getLicence()));
        return idCounter;
    }
}
