package com.example.repository;

import com.example.entity.CommentEntity;
import com.example.mapper.ArticleCommentMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer>{

    Optional<CommentEntity> findByProfileId(Integer profileId);
    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false  where id = :commentId")
    void changeVisible(Integer commentId);

    //Optional<CommentEntity> findByIdAndProfileId(String articleId, Integer profileId);
    @Query("select c from CommentEntity as c where c.article.id = :articleId and c.profile.id = :profileId")
    CommentEntity findByIdAndProfileId( @Param("articleId") String articleId,@Param("profileId") Integer profileId);
    @Query("select new com.example.mapper.ArticleCommentMapper(c.id,c.createdDate,c.updatedDate,c.profile.id,c.profile.name,c.profile.surname) from CommentEntity as c where c.article.id = :articleId")
    ArticleCommentMapper findByArticleId(@Param("articleId") String articleId);

}
