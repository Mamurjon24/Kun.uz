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
    @Query("update ArticleTypeEntity set visible = :visible,prtId =:prtId where id = :id")
    Integer changeArticleVisible(@Param("visible") Boolean visible, @Param("prtId") Integer prtId,@Param("id") Integer id);

    //List<String> findAllByNameUzOrderByCreatedDate(String nameUz);
    @Query("SELECT nameUz FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRu FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEng FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();


}
