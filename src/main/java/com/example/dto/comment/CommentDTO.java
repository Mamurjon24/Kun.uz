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
    private String content;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    //reply_id
}
