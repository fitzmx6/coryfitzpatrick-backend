package com.api.coryfitzpatrick.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // database auto-generated ID
    private Long id;
    private String type;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String url;
    private String videoUrl;
    private String thumbnailImage;
    private List<String> images;
}
