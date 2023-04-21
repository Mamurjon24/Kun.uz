package com.example.dto.articletype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTypeDTO {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEng;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
