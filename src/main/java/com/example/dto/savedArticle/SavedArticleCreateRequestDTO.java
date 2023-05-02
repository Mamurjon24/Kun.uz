package com.example.dto.savedArticle;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SavedArticleCreateRequestDTO {
    @NotBlank(message = "Enter Article Id")
    private String articleId;
}
