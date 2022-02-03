package com.inyoucells.myproj.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
