package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    @Query("FROM ArticleLikeEntity WHERE articleId = :articleId")
    Optional<ArticleLikeEntity> getLikeDisLikeStatus(@Param("articleId") String articleId);
}
