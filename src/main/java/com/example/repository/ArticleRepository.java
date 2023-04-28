package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity,String>, PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByTitle(String title);
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible, status = :status,moderatorId = :moderatorId where id = :id")
    Integer changeVisible(@Param("visible") Boolean visible, @Param("status") ArticleStatus status,@Param("moderatorId") Integer moderatorId, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible, status = :status where id = :id")
    Integer changeStatus(@Param("visible") Boolean visible, @Param("status") ArticleStatus status, @Param("id") String id);

    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 5")
    List<ArticleEntity> findLastFiveArticleByType(@Param("articleTypeId") Integer articleTypeId);
    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 3")
    List<ArticleEntity> findLastThreeArticleByType(@Param("articleTypeId") Integer articleTypeId);


}

