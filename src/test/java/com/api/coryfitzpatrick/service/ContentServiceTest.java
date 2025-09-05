package com.api.coryfitzpatrick.service;

import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.repository.ContentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContentServiceTest {

    @MockitoBean
    private ContentRepository contentRepository;

    @Autowired
    private ContentService contentService;

    @Test
    void findAll_success_returnsListContent() {
        Content contentItem1 = new Content();
        contentItem1.setName("Batman");
        Content contentItem2 = new Content();
        contentItem2.setName("Superman");
        when(contentRepository.findAll()).thenReturn(List.of(contentItem1, contentItem2));

        List<Content> expectedContentList = new ArrayList<>(Arrays.asList(contentItem1, contentItem2));
        assertEquals(expectedContentList, contentService.getContent());
    }
}
