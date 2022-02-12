package com.inyoucells.myproj.service.driver.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDetailResponse {
    private List<DriverDetail> driverDetails;
}
