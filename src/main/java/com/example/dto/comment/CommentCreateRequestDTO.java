package com.example.dto.comment;

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
public class CommentCreateRequestDTO {
    @NotBlank(message = "Enter Content :)")
    private String content;
    @NotBlank(message = "Enter Article Id :)")
    private String articleId;
    private Integer replyId;
}
