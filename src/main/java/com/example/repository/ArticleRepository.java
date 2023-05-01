package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByTitle(String title);

    //4.Change status by id (PUBLISHER) (publish,not_publish)
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible,moderatorId = :moderatorId where id = :id")
    Integer changeVisible(@Param("visible") Boolean visible, @Param("moderatorId") Integer moderatorId, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible, status = :status where id = :id")
    Integer changeStatus(@Param("visible") Boolean visible, @Param("status") ArticleStatus status, @Param("id") String id);

    //5. Get Last 5 Article By Types  ordered_by_created_date
    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 5")
    List<ArticleEntity> findLastFiveArticleByType(@Param("articleTypeId") Integer articleTypeId);

    List<ArticleEntity> findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query("SELECT new ArticleEntity(id,title,description,photoId,publishedDate) From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
    List<ArticleEntity> find5ByTypeId(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:typeId and status =:status order by created_date desc Limit :limit",
            nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNative(@Param("typeId") Integer typeId,
                                              @Param("status") String status,
                                              @Param("limit") Integer limit);

    //6. Get Last 3 Article By Types  ordered_by_created_date
    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 3")
    List<ArticleEntity> findLastThreeArticleByType(@Param("articleTypeId") Integer articleTypeId);

    //7. Get Last 8  Articles witch id not included in given list.

    // @Query("SELECT a FROM ArticleEntity a WHERE a.id NOT IN :idList and a.visible = true ORDER BY a.id DESC")
    @Query(value = "SELECT a.id,a.title,a.description,a.photo_id,a.published_date FROM article AS a  " +
            "where a.id NOT IN :idList and a.visible = true ORDER BY a.created_date DESC limit 8", nativeQuery = true)
    List<ArticleShortInfoMapper> findArticlesNotInList(List<String> idList);

     //9. Get Last 4 Article By Types and except given article id.

    @Query("SELECT new ArticleEntity(a.id,a.title,a.description,a.photoId,a.publishedDate) FROM ArticleEntity as a " +
            " WHERE a.type.id = :articleTypeId and a.id NOT IN :idList ORDER BY a.createdDate DESC limit 3")
    List<ArticleShortInfoMapper> findLast4ArticleByTypes(@Param("articleTypeId") Integer articleTypeId, List<String> idList);



}

