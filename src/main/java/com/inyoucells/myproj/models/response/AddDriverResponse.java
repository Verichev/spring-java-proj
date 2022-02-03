package com.inyoucells.myproj.models.response;

import com.inyoucells.myproj.models.ControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDriverResponse implements ControllerResponse {
    private Long driverId;
}
