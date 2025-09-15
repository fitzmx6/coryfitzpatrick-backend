package com.api.coryfitzpatrick.controller;

import com.api.coryfitzpatrick.service.ContentService;
import com.api.coryfitzpatrick.model.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
@Tag(name = "coryfitzpatrick.com API", description = "Get/Add/Update coryfitzpatrick.com content")
public class ContentController {

   @Autowired
   ContentService contentService;

    @GetMapping
    @Operation(summary = "Get all content", description = "Retrieve all content from the database")
    public List<Content> getContent() {
        return contentService.getContent();
    }

    @GetMapping("/category")
    @Operation(summary = "Get content by category",
            description = "Retrieve all content for a specific category from the database")
    public List<Content> getContentByCategory(@RequestParam String category) {
        return contentService.getContentByCategory(category);
    }

    @PostMapping
    @SecurityRequirement(name = "basicAuth")
    public void createContent(@RequestBody Content content) {
        contentService.addUpdateContent(content);
    }
}
