package com.inyoucells.myproj.models.response;

import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.DriverDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDetailResponse implements ControllerResponse {
    private List<DriverDetail> driverDetails;
}
