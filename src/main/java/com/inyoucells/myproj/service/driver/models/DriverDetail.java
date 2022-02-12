package com.inyoucells.myproj.service.driver.models;

import com.inyoucells.myproj.service.car.models.Car;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverDetail {
    private Long id;
    private final String name;
    private final String licence;
    private List<Car> cars;

    public DriverDetail(String name, String licence) {
        this.name = name;
        this.licence = licence;
    }
}
