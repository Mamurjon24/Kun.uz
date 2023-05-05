package com.example.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCommentPagenationMapper {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Integer profileId;
    private String profileName;
    private String profileSurname;
    private String content;
    private String articleId;
    private String articleTitle;
    private Integer replyId;
}
