package com.api.coryfitzpatrick.integration.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNoAuth_whenGetContent_thenReturns200() throws Exception {
        mockMvc.perform(get("/content"))
                .andExpect(status().isOk());
    }

    @Test
    void givenNoAuth_whenPostContent_thenReturns401() throws Exception {
        mockMvc.perform(post("/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\",\"type\":\"dev\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenInvalidAuth_whenPostContent_thenReturns401() throws Exception {
        mockMvc.perform(post("/content")
                        .with(httpBasic("localUnitTestUser", "wrongpassword"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\",\"type\":\"dev\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenValidAuth_whenPostContent_thenReturns200() throws Exception {
        mockMvc.perform(post("/content")
                        .with(httpBasic("localUnitTestUser", "1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\",\"type\":\"dev\"}"))
                .andExpect(status().isOk());
    }
}
