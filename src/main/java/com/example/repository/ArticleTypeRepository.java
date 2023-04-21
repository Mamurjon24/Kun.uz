package com.example.repository;

import com.example.entity.ArticleTypeEntity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer>, PagingAndSortingRepository<ArticleTypeEntity, Integer> {

    Optional<ArticleTypeEntity> findByNameUzAndNameRuAndNameEng(String nameUz, String nameRu, String nameEng);

    @Transactional
    @Modifying
    @Query("update article_type set visible = :visible where id = :id")
    Integer changeArticleVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    //List<String> findAllByNameUzOrderByCreatedDate(String nameUz);
    @Query("SELECT nameUz FROM article_type ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRu FROM article_type ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEng FROM article_type ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();


}
