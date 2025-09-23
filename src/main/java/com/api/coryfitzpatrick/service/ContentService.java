package com.api.coryfitzpatrick.service;

import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.repository.ContentRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class ContentService {

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public List<Content> getContentByCategory(String category) {
        return contentRepository.findAll().stream()
                .filter(content -> content.getCategory() != null && content.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public Content getContentByUrl(String url) {
        return contentRepository.findAll().stream().filter(content -> content.getUrl().equals(url)).findFirst().orElse(null);
    }

    public void addUpdateContent(Content content) {
        contentRepository.save(content);
    }
}
