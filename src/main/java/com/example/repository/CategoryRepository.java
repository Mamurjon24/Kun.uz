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
    @Query("update CategoryEntity set visible = :visible,prtId =:prtId where id = :id")
    Integer changeRegionVisible(@Param("visible") Boolean visible, @Param("prtId") Integer prtId,@Param("id") Integer id);

    //List<String> findAllByNameUzOrderByCreatedDate(String nameUz);
    @Query("SELECT nameUz FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRu FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEng FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();

}
