package com.api.coryfitzpatrick.service;

import com.api.coryfitzpatrick.model.Content;
import com.api.coryfitzpatrick.repository.ContentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getContent() {
        return contentRepository.findAll();
    }

    public void addUpdateContent(Content content) {
        contentRepository.save(content);
    }
}
