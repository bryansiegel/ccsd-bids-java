package com.bryansiegel.ccsdbidsjava.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvcTest
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_ShouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound()); // 404 because route doesn't exist, but not 401/403

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void adminEndpoints_ShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/admin/dashboard/"))
                .andExpect(status().is3xxRedirection())
                .andExpected(redirectedUrlPattern("**/login"));

        mockMvc.perform(get("/admin/bids"))
                .andExpected(status().is3xxRedirection())
                .andExpected(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    void adminEndpoints_WithAuthentication_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/admin/dashboard/"))
                .andExpected(status().isOk());
    }

    @Test
    void validLogin_ShouldAuthenticateUser() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("password"))
                .andExpected(authenticated().withUsername("user"))
                .andExpected(redirectedUrl("/admin/dashboard/"));
    }

    @Test
    void invalidLogin_ShouldFailAuthentication() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("wrongpassword"))
                .andExpected(unauthenticated())
                .andExpected(redirectedUrl("/login-error"));
    }

    @Test
    @WithMockUser
    void logout_ShouldUnauthenticateUser() throws Exception {
        mockMvc.perform(logout())
                .andExpected(unauthenticated())
                .andExpected(redirectedUrl("/login?logout"));
    }
}