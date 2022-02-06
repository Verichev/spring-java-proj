package com.inyoucells.myproj.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void setup() {
        userRepo.clean();
    }

    @Test
    void signup_alreadyRegistered() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/auth/signup")
                .param("email", "testEmail")
                .param("pass", "otherpass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isBadRequest()).andReturn();
        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);

        assertEquals(new ApiError(HttpStatus.BAD_REQUEST, HttpErrorMessage.EMAIL_IS_ALREADY_TAKEN), apiError);
    }

    @Test
    void signup() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/auth/signup")
                .param("email", "testEmail")
                .param("pass", "otherpass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        String token = result.getResponse().getContentAsString();
        assertTrue(token.contains("_"));
    }

    @Test
    void signin_nonFound() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/auth/login")
                .param("email", "testEmail1")
                .param("pass", "somepass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();
        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals(new ApiError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS), apiError);
    }

    @Test
    void signin_wrongPass() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/auth/login")
                .param("email", "testEmail")
                .param("pass", "somepass1");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();
        ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals(new ApiError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS), apiError);
    }

    @Test
    void signin() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/auth/login")
                .param("email", "testEmail")
                .param("pass", "somepass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();

        String token = result.getResponse().getContentAsString();
        assertTrue(token.contains("_"));
    }
}