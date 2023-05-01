package com.example.dto.savedArticle;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedArticleDTO {
    private Integer id;
    @NonNull()
    private Integer profileId;
    @NotBlank(message = "Enter Article Id")
    private String articleId;
    private LocalDateTime created_date;
}
