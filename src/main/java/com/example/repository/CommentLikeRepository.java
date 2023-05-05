package com.example.repository;

import com.example.entity.CommentLikeEntity;
import com.example.enums.EmotionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {
    Optional<CommentLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Modifying
    @Transactional
    @Query("update CommentLikeEntity  set status =:status where articleId=:articleId and profileId=:profileId")
    int update(@Param("status") EmotionStatus status,
               @Param("articleId") String articleId,
               @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("delete from CommentLikeEntity where articleId=:articleId and profileId=:profileId")
    int delete(String articleId, Integer profileId);
}

