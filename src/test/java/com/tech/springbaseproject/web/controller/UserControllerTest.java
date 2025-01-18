package com.tech.springbaseproject.web.controller;

import com.tech.springbaseproject.service.UserService;
import com.tech.springbaseproject.web.controller.UserController;
import com.tech.springbaseproject.web.dto.CreateUserRequest;
import com.tech.springbaseproject.web.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_whenValidRequest_shouldReturn201() throws Exception {
        CreateUserRequest req = new CreateUserRequest("john_doe", "john@example.com");
        UserResponse res = new UserResponse(1L, "john_doe", "john@example.com");

        Mockito.when(userService.createUser(any(CreateUserRequest.class))).thenReturn(res);

        mockMvc.perform(post("/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("john_doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }
}
