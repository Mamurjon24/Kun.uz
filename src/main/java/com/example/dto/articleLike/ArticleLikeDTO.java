package com.example.dto.articleLike;

import com.example.enums.LikeDislike;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {
    @NotBlank(message = "Enter Article Id :)")
    private String articleId;
    private LikeDislike likeDislike ;
}
