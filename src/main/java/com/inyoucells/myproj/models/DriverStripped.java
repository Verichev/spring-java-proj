package com.inyoucells.myproj.models;

import com.inyoucells.myproj.data.entity.DriverEntity;
import lombok.Data;

@Data
public class DriverStripped {
    private Long id;
    private final String name;
    private final String licence;

    public DriverStripped(DriverEntity driverEntity) {
        id = driverEntity.getId();
        name = driverEntity.getName();
        licence = driverEntity.getLicence();
    }

    public DriverStripped(String name, String licence) {
        this.name = name;
        this.licence = licence;
    }
}
