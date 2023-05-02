package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedArticleMapper {
     private Integer id;
     private String articleId;
     private String articleTitle;
     private String articleDescription;
     private String imageId;
     //private String imageUrl;
}
