package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.models.HttpError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void setup() {
        userRepo.clean();
    }

    @Test
    void signup_alreadyRegistered() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/signup")
                .param("email", "testEmail")
                .param("pass", "otherpass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isBadRequest()).andReturn();

        assertEquals("\"" + HttpError.EMAIL_IS_ALREADY_TAKEN.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    @Test
    void signup() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/signup")
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
        MockHttpServletRequestBuilder requestBuilder = get("/authorize")
                .param("email", "testEmail1")
                .param("pass", "somepass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();

        assertEquals("\"" + HttpError.WRONG_CREDENTIALS.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    @Test
    void signin_wrongPass() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/authorize")
                .param("email", "testEmail")
                .param("pass", "somepass1");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isUnauthorized()).andReturn();

        assertEquals("\"" + HttpError.WRONG_CREDENTIALS.getMessage() + "\"", result.getResponse().getContentAsString());
    }

    @Test
    void signin() throws Exception {
        userRepo.addUser("testEmail", "somepass");
        MockHttpServletRequestBuilder requestBuilder = get("/authorize")
                .param("email", "testEmail")
                .param("pass", "somepass");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();

        String token = result.getResponse().getContentAsString();
        assertTrue(token.contains("_"));
    }
}