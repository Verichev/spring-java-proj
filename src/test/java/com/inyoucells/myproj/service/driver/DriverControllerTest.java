package com.inyoucells.myproj.service.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.DriverFakeProvider;
import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.HttpError;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DriverRepo driverRepo;

    private final DriverFakeProvider driverFakeProvider = new DriverFakeProvider(0);

    @BeforeEach
    void setup() {
        driverRepo.clean();
        driverFakeProvider.reset();
    }

    @Test
    void getDrivers_authOutdated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", "1_1");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();

        assertEquals("\"" + HttpError.AUTHORIZATION_IS_OUTDATED.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    @Test
    void getDrivers_authorized_empty() throws Exception {
        String token = createValidToken();
        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Driver> drivers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Driver[].class));
        assertEquals(Collections.emptyList(), drivers);
    }

    @Test
    void getDrivers_authorized_addDrivers() throws Exception {
        String token = createValidToken();
        Driver driver1 = driverFakeProvider.generateDriver();
        Driver driver2 = driverFakeProvider.generateDriver();
        MockHttpServletRequestBuilder requestBuilder1 = post("/driver")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver1));
        MockHttpServletRequestBuilder requestBuilder2 = post("/driver")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver2));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        mockMvc.perform(requestBuilder2).andExpect(status().isOk());

        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Driver> drivers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Driver[].class));
        assertEquals(Arrays.asList(driver1, driver2), drivers);
    }

    @Test
    void removeDriver() throws Exception {
        String token = createValidToken();
        Driver driver1 = driverFakeProvider.generateDriver();
        Driver driver2 = driverFakeProvider.generateDriver();
        MockHttpServletRequestBuilder requestBuilder1 = post("/driver")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver1));
        MockHttpServletRequestBuilder requestBuilder2 = post("/driver")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver2));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        mockMvc.perform(requestBuilder2).andExpect(status().isOk());

        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<Driver> drivers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Driver[].class));
        assertEquals(List.of(driver1, driver2), drivers);

        MockHttpServletRequestBuilder requestBuilder3 = delete("/driver/{driverId}", driver2.getId())
                .header("token", token);

        mockMvc.perform(requestBuilder3).andExpect(status().isOk());

        requestBuilder = get("/driver").header("token", token);

        resultActions = mockMvc.perform(requestBuilder);

        result = resultActions.andExpect(status().isOk()).andReturn();
        drivers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Driver[].class));
        assertEquals(List.of(driver1), drivers);
    }

    @Test
    void addDriver() throws Exception {
        String token = createValidToken();
        Driver driver1 = driverFakeProvider.generateDriver();
        MockHttpServletRequestBuilder requestBuilder1 = post("/driver")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driver1));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
    }

    private String createValidToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, AuthConsts.TOKEN_EXPIRATION_TIME * 2);
        return "1_" + calendar.getTimeInMillis();
    }
}