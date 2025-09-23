package com.api.coryfitzpatrick.unit.service;

import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.repository.ContentRepository;
import com.api.coryfitzpatrick.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    private ContentService contentService;

    @BeforeEach
    void setUp() {
        contentService = new ContentService(contentRepository);
    }

    @Test
    void getContentByCategory_withValidCategory_returnsMatchingContent() {
        Content movieContent1 = new Content();
        movieContent1.setId(1L);
        movieContent1.setCategory("movie");
        movieContent1.setName("The Dark Knight");
        movieContent1.setUrl("/dark-knight");
        Content movieContent2 = new Content();
        movieContent2.setId(2L);
        movieContent2.setCategory("MOVIE");
        movieContent2.setName("Inception");
        movieContent2.setUrl("/inception");
        Content bookContent = new Content();
        bookContent.setId(3L);
        bookContent.setCategory("book");
        bookContent.setName("Clean Code");
        bookContent.setUrl("/clean-code");
        List<Content> allContent = Arrays.asList(movieContent1, movieContent2, bookContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        List<Content> result = contentService.getContentByCategory("movie");

        assertEquals(2, result.size());
        assertTrue(result.contains(movieContent1));
        assertTrue(result.contains(movieContent2));
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByCategory_withCaseMixedCategory_returnsMatchingContentIgnoreCase() {
        Content movieContent1 = new Content();
        movieContent1.setId(1L);
        movieContent1.setCategory("movie");
        movieContent1.setName("The Dark Knight");
        Content movieContent2 = new Content();
        movieContent2.setId(2L);
        movieContent2.setCategory("MOVIE");
        movieContent2.setName("Inception");
        Content bookContent = new Content();
        bookContent.setId(3L);
        bookContent.setCategory("book");
        bookContent.setName("Clean Code");
        List<Content> allContent = Arrays.asList(movieContent1, movieContent2, bookContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        List<Content> result = contentService.getContentByCategory("MoViE");

        assertEquals(2, result.size());
        assertTrue(result.contains(movieContent1));
        assertTrue(result.contains(movieContent2));
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByCategory_withNonExistentCategory_returnsEmptyList() {
        Content movieContent = new Content();
        movieContent.setCategory("movie");
        movieContent.setName("The Dark Knight");
        Content bookContent = new Content();
        bookContent.setCategory("book");
        bookContent.setName("Clean Code");
        List<Content> allContent = Arrays.asList(movieContent, bookContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        List<Content> result = contentService.getContentByCategory("music");

        assertTrue(result.isEmpty());
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByCategory_withNullCategory_returnsEmptyList() {
        Content movieContent = new Content();
        movieContent.setCategory("movie");
        movieContent.setName("The Dark Knight");
        Content contentWithoutCategory = new Content();
        contentWithoutCategory.setCategory(null);
        contentWithoutCategory.setName("No Category Content");
        List<Content> allContent = Arrays.asList(movieContent, contentWithoutCategory);
        when(contentRepository.findAll()).thenReturn(allContent);

        List<Content> result = contentService.getContentByCategory(null);

        assertTrue(result.isEmpty());
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByCategory_withEmptyRepository_returnsEmptyList() {
        when(contentRepository.findAll()).thenReturn(Collections.emptyList());

        List<Content> result = contentService.getContentByCategory("movie");

        assertTrue(result.isEmpty());
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByCategory_filtersOutContentWithNullCategory() {
        Content movieContent = new Content();
        movieContent.setCategory("movie");
        movieContent.setName("The Dark Knight");
        Content contentWithoutCategory = new Content();
        contentWithoutCategory.setCategory(null);
        contentWithoutCategory.setName("No Category Content");
        List<Content> allContent = Arrays.asList(movieContent, contentWithoutCategory);
        when(contentRepository.findAll()).thenReturn(allContent);

        List<Content> result = contentService.getContentByCategory("movie");

        assertEquals(1, result.size());
        assertEquals(movieContent, result.getFirst());
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByUrl_withValidUrl_returnsMatchingContent() {
        Content movieContent = new Content();
        movieContent.setName("The Dark Knight");
        movieContent.setUrl("/dark-knight");
        Content bookContent = new Content();
        bookContent.setName("Clean Code");
        bookContent.setUrl("/clean-code");
        List<Content> allContent = Arrays.asList(movieContent, bookContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        Content result = contentService.getContentByUrl("/dark-knight");

        assertNotNull(result);
        assertEquals(movieContent, result);
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByUrl_withNonExistentUrl_returnsNull() {
        Content movieContent = new Content();
        movieContent.setUrl("/dark-knight");
        Content bookContent = new Content();
        bookContent.setUrl("/clean-code");
        List<Content> allContent = Arrays.asList(movieContent, bookContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        Content result = contentService.getContentByUrl("/non-existent");

        assertNull(result);
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByUrl_withNullUrl_returnsNull() {
        Content movieContent = new Content();
        movieContent.setUrl("/dark-knight");
        List<Content> allContent = List.of(movieContent);
        when(contentRepository.findAll()).thenReturn(allContent);

        Content result = contentService.getContentByUrl(null);

        assertNull(result);
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByUrl_withEmptyRepository_returnsNull() {
        when(contentRepository.findAll()).thenReturn(Collections.emptyList());

        Content result = contentService.getContentByUrl("/any-url");

        assertNull(result);
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void getContentByUrl_withContentHavingNullUrl_handlesGracefully() {
        Content movieContent = new Content();
        movieContent.setUrl("/dark-knight");
        movieContent.setName("The Dark Knight");
        Content contentWithoutUrl = new Content();
        contentWithoutUrl.setUrl(null);
        contentWithoutUrl.setName("No URL Content");
        List<Content> allContent = Arrays.asList(movieContent, contentWithoutUrl);
        when(contentRepository.findAll()).thenReturn(allContent);

        assertDoesNotThrow(() -> {
            Content result = contentService.getContentByUrl("/dark-knight");
            assertEquals(movieContent, result);
        });
        verify(contentRepository, times(1)).findAll();
    }

    @Test
    void addUpdateContent_withValidContent_savesContent() {
        Content newContent = new Content();
        newContent.setName("Test Content");
        newContent.setCategory("test");

        contentService.addUpdateContent(newContent);

        verify(contentRepository, times(1)).save(newContent);
    }


    @Test
    void addUpdateContent_withExistingContent_updatesContent() {
        Content existingContent = new Content();
        existingContent.setId(1L);
        existingContent.setName("Existing Content");
        existingContent.setDescription("Updated description");

        contentService.addUpdateContent(existingContent);

        verify(contentRepository, times(1)).save(existingContent);
    }

    @Test
    void getContentRepository_returnsCorrectRepository() {
        ContentRepository result = contentService.getContentRepository();

        assertEquals(contentRepository, result);
    }
}