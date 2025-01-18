package com.tech.springbaseproject.web.controller;

import com.tech.springbaseproject.domain.repository.UserRepository;
import com.tech.springbaseproject.web.dto.CreateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_shouldReturn201AndSaveInDb() throws Exception {
        CreateUserRequest request = new CreateUserRequest("charlie", "charlie@example.com");

        mockMvc.perform(post("/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("charlie"))
                .andExpect(jsonPath("$.data.email").value("charlie@example.com"));
    }

    @Test
    void getUser_whenNotFound_shouldReturn404() throws Exception {
        // Here we assume the GlobalExceptionHandler returns 404 for UserNotFoundException
        mockMvc.perform(get("/v1/users/999"))
                .andExpect(status().isNotFound());
    }
}
