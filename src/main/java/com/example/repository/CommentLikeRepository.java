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
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer commentId, Integer profileId);

    @Modifying
    @Transactional
    @Query("update CommentLikeEntity  set status =:status where commentId=:commentId and profileId=:profileId")
    int update(@Param("status") EmotionStatus status,
               @Param("commentId") Integer commentId,
               @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("delete from CommentLikeEntity where commentId=:commentId and profileId=:profileId")
    int delete(Integer commentId, Integer profileId);
}

