package com.inyoucells.myproj.models.response;

import com.inyoucells.myproj.models.DriverDetail;

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
