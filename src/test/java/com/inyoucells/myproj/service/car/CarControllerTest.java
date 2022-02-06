package com.inyoucells.myproj.service.car;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.CarFakeProvider;
import com.inyoucells.myproj.data.CarRepo;
import com.inyoucells.myproj.data.DriverRepo;
import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.models.Car;
import com.inyoucells.myproj.models.Driver;
import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.response.CarResponse;
import com.inyoucells.myproj.service.auth.TokenValidator;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private TokenValidator tokenValidator;

    private final CarFakeProvider carFakeProvider = new CarFakeProvider(0);

    private String token;
    private long driverId;

    @BeforeEach
    void setup() {
        carRepo.clean();
        carFakeProvider.reset();
        userRepo.clean();
        Optional<String> token = userRepo.addUser("testEmail", "pass");
        assertTrue(token.isPresent());
        long userId = tokenValidator.parseUserId(token.get());
        this.driverId = driverRepo.addDriver(userId, new Driver("name", "licence"));
        this.token = token.get();
    }

    @Test
    void getCars_authOutdated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/car").header("token", "1_1")
                .param("driverId", String.valueOf(driverId));
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();

        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals(new ApiError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.AUTHORIZATION_IS_OUTDATED),
                apiError);
    }

    @Test
    void getCars_authorized_empty() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        assertEquals(Collections.emptyList(), carResponse.getCars());
    }

    @Test
    void getCars_authorized_addCars() throws Exception {
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        car1.setDriverId(driverId);
        car2.setDriverId(driverId);
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

        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        List<Car> cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(car1, car2), cars);
    }

    @Test
    void removeCar() throws Exception {
        Car car1 = carFakeProvider.generateCar();
        Car car2 = carFakeProvider.generateCar();
        car1.setDriverId(driverId);
        car2.setDriverId(driverId);
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

        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        UUID secondCarId = carResponse.getCars().get(1).getId();
        List<Car> cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(car1, car2), cars);

        MockHttpServletRequestBuilder requestBuilder3 = delete("/car/{carId}", secondCarId)
                .header("token", token);

        mockMvc.perform(requestBuilder3).andExpect(status().isOk());

        requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        resultActions = mockMvc.perform(requestBuilder);

        result = resultActions.andExpect(status().isOk()).andReturn();
        carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(car1), cars);
    }

    @Test
    void addCar() throws Exception {
        Car car1 = carFakeProvider.generateCar();
        car1.setDriverId(driverId);
        MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/car")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car1));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
    }

    @Test
    void addCar_missingDriverId() throws Exception {
        Car car = carFakeProvider.generateCar();
        car.setDriverId(null);
        MockHttpServletRequestBuilder requestBuilder = post("/car").header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car));

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isBadRequest()).andReturn();
        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals(new ApiError(HttpStatus.BAD_REQUEST, HttpErrorMessage.MISSING_DRIVER_ID),
                apiError);
    }

    @Test
    void searchCarsByBrand() throws Exception {
        List<Car> initialCars = Stream.generate(carFakeProvider::generateCar).limit(3)
                .peek(car -> car.setDriverId(driverId))
                .collect(Collectors.toList());
        initialCars.get(0).setBrand("BMW");
        initialCars.get(1).setBrand("Mercedes");
        initialCars.get(2).setBrand("HBMW1");
        for (Car car : initialCars) {
            MockHttpServletRequestBuilder requestBuilder = post("/car")
                    .header("token", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(car));

            mockMvc.perform(requestBuilder).andExpect(status().isOk());
        }
        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        List<Car> cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(initialCars, cars);

        String keyword = "BMW";
        MockHttpServletRequestBuilder requestBuilder1 = get("/car/search/brand")
                .param("keyword", keyword)
                .header("token", token);

        MvcResult resultActions = mockMvc.perform(requestBuilder1).andExpect(status().isOk()).andReturn();

        carResponse = objectMapper.readValue(resultActions.getResponse().getContentAsString(), CarResponse.class);
        cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(initialCars.get(0), initialCars.get(2)), cars);
    }

    @Test
    void getCarsByYearAndBrand() throws Exception {
        List<Car> initialCars = Stream.generate(carFakeProvider::generateCar).limit(3)
                .peek(car -> car.setDriverId(driverId))
                .collect(Collectors.toList());
        initialCars.get(0).setBrand("BMW");
        initialCars.get(0).setYear("2001");
        initialCars.get(1).setBrand("Mercedes");
        initialCars.get(1).setYear("2001");
        initialCars.get(2).setBrand("BMW");
        initialCars.get(2).setYear("2002");
        for (Car car : initialCars) {
            MockHttpServletRequestBuilder requestBuilder = post("/car")
                    .header("token", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(car));

            mockMvc.perform(requestBuilder).andExpect(status().isOk());
        }
        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        List<Car> cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(initialCars, cars);

        String year = "2001";
        String brand = "BMW";
        MockHttpServletRequestBuilder requestBuilder1 = get("/car/yearbrand")
                .param("year", year)
                .param("brand", brand)
                .header("token", token);

        MvcResult resultActions = mockMvc.perform(requestBuilder1).andExpect(status().isOk()).andReturn();

        carResponse = objectMapper.readValue(resultActions.getResponse().getContentAsString(), CarResponse.class);
        cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(initialCars.get(0)), cars);
    }

    @Test
    void getCarsMoreHorsePower() throws Exception {
        List<Car> initialCars = Stream.generate(carFakeProvider::generateCar).limit(3)
                .peek(car -> car.setDriverId(driverId))
                .collect(Collectors.toList());
        initialCars.get(0).setHorsepower(50);
        initialCars.get(1).setHorsepower(51);
        initialCars.get(2).setHorsepower(49);
        for (Car car : initialCars) {
            MockHttpServletRequestBuilder requestBuilder = post("/car")
                    .header("token", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(car));

            mockMvc.perform(requestBuilder).andExpect(status().isOk());
        }
        MockHttpServletRequestBuilder requestBuilder = get("/car")
                .header("token", token)
                .param("driverId", String.valueOf(driverId));

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        CarResponse carResponse = objectMapper.readValue(result.getResponse().getContentAsString(), CarResponse.class);
        List<Car> cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(initialCars, cars);

        int fromHorsepower = 50;
        MockHttpServletRequestBuilder requestBuilder1 = get("/car/morehorsepower")
                .param("minHorsePower", String.valueOf(fromHorsepower))
                .header("token", token);

        MvcResult resultActions = mockMvc.perform(requestBuilder1).andExpect(status().isOk()).andReturn();

        carResponse = objectMapper.readValue(resultActions.getResponse().getContentAsString(), CarResponse.class);
        cars = carResponse.getCars().stream().peek(item -> item.setId(null)).collect(
                Collectors.toList());
        assertEquals(List.of(initialCars.get(0), initialCars.get(1)), cars);
    }
}