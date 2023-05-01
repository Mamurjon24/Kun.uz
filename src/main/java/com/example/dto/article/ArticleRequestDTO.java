package com.example.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {
    @NotNull(message = "title required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @NotBlank(message = "Field must have some value")
    private String description;
    @NotEmpty(message = "Content qani")
    private String content;
    @NotEmpty(message = "Photo qani")
    private String photoId;
    @NotNull(message = "Region qani")
    private Integer regionId;
    @NotNull(message = "Category qani")
    private Integer categoryId;
    @NotNull(message = "Should provide value")
    private Integer typeId;

}
