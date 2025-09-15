package com.api.coryfitzpatrick.integration.controller;

import com.api.coryfitzpatrick.controller.ContentController;
import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.View;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContentService contentService;
    @Autowired
    private View error;

    @Test
    void getContent_statusOkContentFound_returnsListContent() throws Exception {
        Content content = new Content();
        content.setName("Joker");
        when(contentService.getContent()).thenReturn(List.of(content));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/content")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = "[{\"id\":null,\"category\":null,\"name\":\"Joker\"," +
        "\"description\":null,\"url\":null,\"videoUrl\":null,\"thumbnailImage\":null,\"images\":null}]";
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void getContent_statusOkNoContentFound_returnsListContent() throws Exception {
        when(contentService.getContent()).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/content")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals("", actualResponseBody);
    }

    @Test
    void getContentByCategory_statusOkContentFound_returnsListContent() throws Exception {
        Content content = new Content();
        content.setName("Joker");
        content.setCategory("movie");
        when(contentService.getContentByCategory("movie")).thenReturn(List.of(content));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/content/category")
                                .param("category", "movie")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = "[{\"id\":null,\"category\":\"movie\",\"name\":\"Joker\"," +
                "\"description\":null,\"url\":null,\"videoUrl\":null,\"thumbnailImage\":null,\"images\":null}]";
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void getContentByCategory_statusOkNoContentFound_returnsListContent() throws Exception {
        when(contentService.getContent()).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/content/category")
                                .param("category", "movie")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals("[]", actualResponseBody);
    }

    @Test
    void getContentByCategory_withNoParam_returnsBadRequest() throws Exception {
        when(contentService.getContent()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/content/category")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
