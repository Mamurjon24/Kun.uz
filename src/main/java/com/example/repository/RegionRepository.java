package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer>, PagingAndSortingRepository<RegionEntity, Integer> {
    Optional<RegionEntity> findByNameUzAndNameRuAndNameEng(String nameUz, String nameRu, String nameEng);

    @Transactional
    @Modifying
    @Query("update region set visible = :visible where id = :id")
    Integer changeRegionVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    //List<String> findAllByNameUzOrderByCreatedDate(String nameUz);
    @Query("SELECT nameUz FROM region ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRu FROM region ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEng FROM region ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();
}
