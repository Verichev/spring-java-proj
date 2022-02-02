package com.inyoucells.myproj.service.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.CarFakeProvider;
import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.service.auth.AuthConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepo carRepo;

    private final CarFakeProvider carFakeProvider = new CarFakeProvider(0);

    @BeforeEach
    void setup() {
        carRepo.clean();
        carFakeProvider.reset();
    }

    @Test
    void getCars_authOutdated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/car").header("token", "1_1");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();

        assertEquals("\"" + HttpErrorMessage.AUTHORIZATION_IS_OUTDATED.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    @Test
    void getCars_authorized_empty() throws Exception {
        String token = createValidToken();
        MockHttpServletRequestBuilder requestBuilder = get("/car").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Car> cars = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Car[].class));
        assertEquals(Collections.emptyList(), cars);
    }

    @Test
    void getCars_authorized_addCars() throws Exception {
        String token = createValidToken();
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        MockHttpServletRequestBuilder requestBuilder1 = post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car1));
        MockHttpServletRequestBuilder requestBuilder2 = post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car2));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        mockMvc.perform(requestBuilder2).andExpect(status().isOk());

        MockHttpServletRequestBuilder requestBuilder = get("/car").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Car> cars = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Car[].class));
        assertEquals(Arrays.asList(car1, car2), cars);
    }

    @Test
    void removeCar() throws Exception {
        String token = createValidToken();
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        MockHttpServletRequestBuilder requestBuilder1 = post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car1));
        MockHttpServletRequestBuilder requestBuilder2 = post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car2));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        mockMvc.perform(requestBuilder2).andExpect(status().isOk());

        MockHttpServletRequestBuilder requestBuilder = get("/car").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Car> cars = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Car[].class));
        assertEquals(List.of(car1, car2), cars);

        MockHttpServletRequestBuilder requestBuilder3 = delete("/car/{carId}", car2.getId())
                .header("token", token);

        mockMvc.perform(requestBuilder3).andExpect(status().isOk());

        requestBuilder = get("/car").header("token", token);

        resultActions = mockMvc.perform(requestBuilder);

        result = resultActions.andExpect(status().isOk()).andReturn();
        cars = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Car[].class));
        assertEquals(List.of(car1), cars);
    }

    @Test
    void addCar() throws Exception {
        String token = createValidToken();
        Car car1 = carFakeProvider.generateCar();
        MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car1));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
    }

    @Test
    void addCar_missingDriverId() throws Exception {
        String token = createValidToken();
        Car car = carFakeProvider.generateCar();
        car.setDriverId(null);
        MockHttpServletRequestBuilder requestBuilder = post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isBadRequest()).andReturn();
        assertEquals("\"" + HttpErrorMessage.MISSING_DRIVER_ID.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    private String createValidToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, AuthConsts.TOKEN_EXPIRATION_TIME * 2);
        return "1_" + calendar.getTimeInMillis();
    }
}