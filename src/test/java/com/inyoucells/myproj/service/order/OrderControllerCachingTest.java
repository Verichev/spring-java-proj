package com.inyoucells.myproj.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.service.auth.TokenValidator;
import com.inyoucells.myproj.service.order.data.OrderRepository;
import com.inyoucells.myproj.service.order.model.OrderResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Import(TestConfiguration.class)
@AutoConfigureMockMvc
class OrderControllerCachingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private FakeSlowOrderService orderService;

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

        for (int i = 0; i < 5; i++) {
            ResultActions resultActions = mockMvc.perform(requestBuilder);
            MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
            List<OrderResponse> orders = Arrays.asList(
                    objectMapper.readValue(result.getResponse().getContentAsString(), OrderResponse[].class));
            assertEquals(Collections.emptyList(), orders);
        }
    }

    private Long createValidToken() {
        userRepo.clean();
        String token = userRepo.addUser("testEmail", "pass");
        return tokenValidator.parseUserId(token);
    }
}