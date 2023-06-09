package com.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CommentDTO {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private String content;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Integer replyId;
}
