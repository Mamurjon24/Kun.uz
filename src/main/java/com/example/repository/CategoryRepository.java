package com.example.repository;

import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>, PagingAndSortingRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameUzAndNameRuAndNameEng(String nameUz, String nameRu, String nameEng);
    @Transactional
    @Modifying
    @Query("update category set visible = :visible where id = :id")
    Integer changeRegionVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    //List<String> findAllByNameUzOrderByCreatedDate(String nameUz);
    @Query("SELECT nameUz FROM category ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRu FROM category ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEng FROM category ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();

}
