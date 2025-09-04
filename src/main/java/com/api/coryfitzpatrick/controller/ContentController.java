package com.api.coryfitzpatrick.controller;

import com.api.coryfitzpatrick.ContentService;
import com.api.coryfitzpatrick.model.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "coryfitzpatrick.com API", description = "Get coryfitzpatrick.com content")
public class ContentController {

   @Autowired
   ContentService contentService;

    @GetMapping("/content")
    @Operation(summary = "Get all content", description = "Retrieve all content from the database")
    public List<Content> getContent() {
        return contentService.getContent();
    }
}
