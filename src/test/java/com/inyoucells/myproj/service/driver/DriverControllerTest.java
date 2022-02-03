package com.inyoucells.myproj.service.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.DriverFakeProvider;
import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.response.AddDriverResponse;
import com.inyoucells.myproj.models.response.DriverResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Autowired
    private UserRepo userRepo;

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
        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals(new ApiError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.AUTHORIZATION_IS_OUTDATED), apiError);
    }

    @Test
    void getDrivers_authorized_empty() throws Exception {
        String token = createValidToken();
        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", token);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        DriverResponse driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertEquals(Collections.emptyList(), driverResponse.getDrivers());
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
        DriverResponse driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertTrue(driverResponse.getDrivers().stream().map(driver -> new Driver(driver.getName(), driver.getLicence())).collect(Collectors.toList()).containsAll(Arrays.asList(driver1, driver2)));
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
        ResultActions resultActions = mockMvc.perform(requestBuilder2).andExpect(status().isOk());
        AddDriverResponse addDriverResponse = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), AddDriverResponse.class);

        MockHttpServletRequestBuilder requestBuilder = get("/driver").header("token", token);

        resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        DriverResponse driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertTrue(driverResponse.getDrivers().stream().map(driver -> new Driver(driver.getName(), driver.getLicence())).collect(Collectors.toList()).containsAll(Arrays.asList(driver1, driver2)));

        MockHttpServletRequestBuilder requestBuilder3 = delete("/driver/{driverId}", addDriverResponse.getDriverId())
                .header("token", token);

        mockMvc.perform(requestBuilder3).andExpect(status().isOk());

        requestBuilder = get("/driver").header("token", token);

        resultActions = mockMvc.perform(requestBuilder);

        result = resultActions.andExpect(status().isOk()).andReturn();
        driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertTrue(driverResponse.getDrivers().stream().map(driver -> new Driver(driver.getName(), driver.getLicence())).collect(Collectors.toList()).contains(driver1));

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

    @Test
    void getDrivers_WithPagination() throws Exception {
        String token = createValidToken();
        List<Driver> drivers = Stream.generate(driverFakeProvider::generateDriver).limit(20).collect(Collectors.toList());
        for (Driver driver : drivers) {
            MockHttpServletRequestBuilder requestBuilder1 = post("/driver")
                    .header("token", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(driver));
            mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        }

        MockHttpServletRequestBuilder requestBuilder = get("/driver")
                .header("token", token)
                .param("page", "0")
                .param("size", "10");

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        DriverResponse driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertTrue(driverResponse.getDrivers().stream().map(driver -> new Driver(driver.getName(), driver.getLicence())).collect(Collectors.toList()).containsAll(drivers.subList(0, 9)));

        requestBuilder = get("/driver")
                .header("token", token)
                .param("page", "1")
                .param("size", "10");

        resultActions = mockMvc.perform(requestBuilder);
        result = resultActions.andExpect(status().isOk()).andReturn();
        driverResponse = objectMapper.readValue(result.getResponse().getContentAsString(), DriverResponse.class);
        assertTrue(driverResponse.getDrivers().stream().map(driver -> new Driver(driver.getName(), driver.getLicence())).collect(Collectors.toList()).containsAll(drivers.subList(10, 19)));

    }

    private String createValidToken() {
        userRepo.clean();
        Optional<String> token = userRepo.addUser("testEmail", "pass");
        assertTrue(token.isPresent());
        return token.get();
    }
}