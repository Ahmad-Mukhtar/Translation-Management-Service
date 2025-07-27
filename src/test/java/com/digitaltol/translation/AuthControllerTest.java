package com.digitaltol.translation;


import com.digitaltol.translation.controller.AuthController;
import com.digitaltol.translation.dto.AuthRequest;
import com.digitaltol.translation.security.JwtUtil;
import com.digitaltol.translation.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void loginSuccess() throws Exception {
        Mockito.when(authService.authenticate("user", "pass")).thenReturn("token123");
        var req = new AuthRequest("user", "pass");
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("token123")));
    }

    @Test
    void loginFail() throws Exception {
        Mockito.when(authService.authenticate("user", "wrong")).thenThrow(new IllegalArgumentException("Invalid creds"));
        var req = new AuthRequest("user", "wrong");
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().is5xxServerError());
    }
}