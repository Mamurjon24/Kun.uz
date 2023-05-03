package com.example.dto.region;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class RegionDTO {
    @NotNull(message = "Id required")
    private Integer id;
    @NotNull(message = "Name in Uzb must not be empty")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameUz;
    @NotNull(message = "Name in Ru must not be empty")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameRu;
    @NotNull(message = "Name in Eng must not be empty")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameEng;
    @NotNull(message = "Title required")
    private Boolean visible;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;
    private String name;

}
