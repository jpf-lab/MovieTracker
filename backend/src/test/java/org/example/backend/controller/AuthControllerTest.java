package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getMe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
                        .with(oidcLogin()
                                .userInfoToken(token -> token
                                        .claim("login", "user")
                                )))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user"));
    }
}