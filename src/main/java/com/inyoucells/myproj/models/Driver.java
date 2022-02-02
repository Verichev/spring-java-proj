package com.inyoucells.myproj.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Driver {
    private Long id;
    private final String name;
    private final String licence;
    private List<Car> cars;

    public Driver(String name, String licence) {
        this.name = name;
        this.licence = licence;
    }
}
