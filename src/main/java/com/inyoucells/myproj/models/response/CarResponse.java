package com.inyoucells.myproj.models.response;

import com.inyoucells.myproj.models.Car;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private List<Car> cars;
}
