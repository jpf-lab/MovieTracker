package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getMe() throws Exception {
        mockMvc.perform(get("/api/auth/me")
                        .with(oidcLogin()
                                .userInfoToken(token -> token
                                        .claim("login", "testUser")
                                        .claim("avatar_url", "testAvatarUrl")
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "username": "testUser",
                            "avatarUrl": "testAvatarUrl"
                        }
                        """));
    }
}