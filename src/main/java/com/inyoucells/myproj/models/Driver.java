package com.inyoucells.myproj.models;

import com.inyoucells.myproj.data.entity.DriverEntity;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Driver {
    private Long id;
    private final String name;
    private final String licence;

    @Nullable
    private List<Car> cars = null;

    public Driver(DriverEntity driverEntity, boolean includeCars) {
        id = driverEntity.getId();
        name = driverEntity.getName();
        licence = driverEntity.getLicence();
        if (includeCars) {
            cars = driverEntity.getCars().stream().map(Car::new).collect(Collectors.toList());
        }
    }

    public Driver(String name, String licence) {
        this.name = name;
        this.licence = licence;
    }
}
