package com.example.repository.profile;

import com.example.entity.SavedArticleEntity;
import com.example.mapper.SavedArticleMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {
    @Query("select new com.example.mapper.SavedArticleMapper(s.id,s.article.id,s.article.title,s.article.description,s.article.image.id) from SavedArticleEntity as s ")
    SavedArticleMapper findAllSavedArticle();
}
