package com.example.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleUpdateRequestDTO {
    @NotNull(message = "Id required")
    private String id;
    @NotNull(message = "Title required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @NotBlank(message = "Field must have some value")
    private String description;
    @NotEmpty(message = "Content qani")
    private String content;
    @NotEmpty(message = "Image qani")
    private String imageId;
    @NotEmpty(message = "Region qani")
    private Integer regionId;
    @NotEmpty(message = "Category qani")
    private Integer categoryId;
    @NotEmpty(message = "Should provide value")
    private Integer typeId;
}
