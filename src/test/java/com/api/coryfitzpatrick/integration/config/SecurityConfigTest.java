package com.api.coryfitzpatrick.integration.config;

import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ContentService contentService;

    @Test
    void getContentByCategory_withNoAuth_returns200() throws Exception {
        mockMvc.perform(get("/content/category?category=dev"))
                .andExpect(status().isOk());
    }

    @Test
    void getContentByUrl_withNoAuthContentFound_returns200() throws Exception {
        Content content = new Content();
        content.setName("Test Content");
        content.setUrl("/test-url");
        when(contentService.getContentByUrl("/test-url")).thenReturn(content);

        mockMvc.perform(get("/content/item?url=/test-url"))
                .andExpect(status().isOk());
    }

    @Test
    void getContentByUrl_withNoAuthNoContentFound_returns404() throws Exception {
        when(contentService.getContentByUrl("/test-url")).thenReturn(null);

        mockMvc.perform(get("/content/item?url=/test-url"))
                .andExpect(status().isNotFound());
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
