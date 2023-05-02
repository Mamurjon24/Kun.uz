package com.example.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequestDTO {
    @NotBlank(message = "Enter Content :)")
    private String content;
    @NotBlank(message = "Enter Article Id :)")
    private String articleId;

    //private Integer reply_id;
}
