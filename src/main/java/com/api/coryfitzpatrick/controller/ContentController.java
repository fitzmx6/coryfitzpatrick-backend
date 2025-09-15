package com.api.coryfitzpatrick.controller;

import com.api.coryfitzpatrick.service.ContentService;
import com.api.coryfitzpatrick.model.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/content")
@Tag(name = "coryfitzpatrick.com API", description = "Get/Add/Update coryfitzpatrick.com content")
public class ContentController {

   @Autowired
   ContentService contentService;

    @GetMapping("/category")
    @Operation(summary = "Get content by category",
            description = "Retrieve all content for a specific category from the database")
    public List<Content> getContentByCategory(@RequestParam String category) {
        return contentService.getContentByCategory(category);
    }

    @GetMapping("/item")
    @Operation(summary = "Get individual content item by url",
            description = "Retrieve specific content from the database")
    public ResponseEntity<Content> getContentByUrl(@RequestParam String url) {
        Content content = contentService.getContentByUrl(url);
        if (content == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(content);
    }

    @PostMapping
    @SecurityRequirement(name = "basicAuth")
    public void createContent(@RequestBody Content content) {
        contentService.addUpdateContent(content);
    }
}
