package com.inyoucells.myproj.service.driver.models;

import com.inyoucells.myproj.service.driver.data.DriverEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    private Long id;
    private String name;
    private String licence;

    public Driver(DriverEntity driverEntity) {
        id = driverEntity.getId();
        name = driverEntity.getName();
        licence = driverEntity.getLicence();
    }

    public Driver(String name, String licence) {
        this.name = name;
        this.licence = licence;
    }
}
