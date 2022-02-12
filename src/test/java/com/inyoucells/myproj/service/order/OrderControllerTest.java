package com.inyoucells.myproj.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.service.auth.TokenValidator;
import com.inyoucells.myproj.service.auth.data.repo.UserRepo;
import com.inyoucells.myproj.service.order.data.OrderRepository;
import com.inyoucells.myproj.service.order.model.Order;
import com.inyoucells.myproj.service.order.model.OrderResponse;

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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TokenValidator tokenValidator;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
    }

    @Test
    void getOrder_empty() throws Exception {
        String userId = String.valueOf(createValidToken());
        MockHttpServletRequestBuilder requestBuilder = get("/order").param("userId", userId);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<OrderResponse> orders = Arrays.asList(
                objectMapper.readValue(result.getResponse().getContentAsString(), OrderResponse[].class));
        assertEquals(Collections.emptyList(), orders);
    }

    @Test
    void getOrder_addOrders() throws Exception {
        String userId = String.valueOf(createValidToken());
        Order order1 = new Order().name("name1").date("date1");
        Order order2 = new Order().name("name2").date("date2");
        MockHttpServletRequestBuilder requestBuilder1 = post("/order")
                .param("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order1));
        MockHttpServletRequestBuilder requestBuilder2 = post("/order")
                .param("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order2));

        mockMvc.perform(requestBuilder1).andExpect(status().isOk());
        mockMvc.perform(requestBuilder2).andExpect(status().isOk());

        MockHttpServletRequestBuilder requestBuilder = get("/order").param("userId", userId);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        List<OrderResponse> drivers = Arrays.asList(
                objectMapper.readValue(result.getResponse().getContentAsString(), OrderResponse[].class));
        assertTrue(drivers.stream().map(order -> new Order().name(order.getName()).date(order.getDate()))
                .collect(Collectors.toList()).containsAll(Arrays.asList(order1, order2)));
    }

    private Long createValidToken() {
        userRepo.clean();
        String token = userRepo.addUser("testEmail", "pass");
        return tokenValidator.parseUserId(token);
    }
}