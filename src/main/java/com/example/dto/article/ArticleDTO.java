package com.example.dto.article;

import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer regionId;
    private Integer articleTypeId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private String imageId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    //image_id,
}
