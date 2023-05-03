package com.example.dto.savedArticle;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedArticleDTO {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime created_date;
}
