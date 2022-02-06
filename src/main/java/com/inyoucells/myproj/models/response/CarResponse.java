package com.inyoucells.myproj.models.response;

import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.ControllerResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse implements ControllerResponse {
    private List<Car> cars;
}
