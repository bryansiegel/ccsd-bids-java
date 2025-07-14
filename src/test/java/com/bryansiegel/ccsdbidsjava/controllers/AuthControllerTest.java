package com.bryansiegel.ccsdbidsjava.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/login"));
    }

    @Test
    void loginError_ShouldReturnLoginErrorView() throws Exception {
        mockMvc.perform(get("/login-error/"))
                .andExpected(status().isOk())
                .andExpected(view().name("admin/login-error"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void dashboard_ShouldReturnDashboardWithUser() throws Exception {
        mockMvc.perform(get("/admin/dashboard/"))
                .andExpected(status().isOk())
                .andExpected(view().name("admin/dashboard"))
                .andExpected(model().attributeExists("user"))
                .andExpected(model().attribute("user", "testuser"));
    }
}