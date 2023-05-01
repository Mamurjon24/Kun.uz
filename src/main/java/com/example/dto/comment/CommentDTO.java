package com.example.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private String content;
    private Boolean visible;

    //reply_id
}
