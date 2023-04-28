package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.dto.article.ArticleUpdateRequestDTO;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer moderId) {
        // check
//        ProfileEntity moderator = profileService.get(moderId);
//        RegionEntity region = regionService.get(dto.getRegionId());
//        CategoryEntity category = categoryService.get(dto.getCategoryId());
        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if(optional.isEmpty()) {
            throw new ItemNotFoundException(" This Title is already use bro! :)");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderId);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setTypeId(dto.getTypeId());
        articleRepository.save(entity);
        return dto;
    }

    public boolean update(ArticleUpdateRequestDTO dto, Integer id) {
        Optional<ArticleEntity> optional = articleRepository.findById(dto.getId());
        if(optional.isEmpty()) {
            throw new ItemNotFoundException(" Title must be in Database :)");
        }
        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(id);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setTypeId(dto.getTypeId());
        articleRepository.save(entity);
        return true;
    }

    public ArticleEntity get(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Article not found: " + id);
        }
        return optional.get();
    }

    public Integer delete(Integer modetorId , String articleId) {
        get(articleId);
        Integer response = articleRepository.changeVisible(Boolean.FALSE, ArticleStatus.NOTPUBLISHED,modetorId ,articleId);
        return response;
    }

    public Boolean changeStatus(String id, Integer publisherId) {
        ArticleEntity entity = get(id);
        entity.setPublisherId(publisherId);
        entity.setPublishedDate(LocalDateTime.now());
        entity.setStatus(ArticleStatus.PUBLISHED);
        articleRepository.changeStatus(Boolean.TRUE, ArticleStatus.PUBLISHED, id);
        return true;
    }

    public List<ArticleDTO> findLastFiveArticleByType(Integer articleTypeId) {
        List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLastFiveArticleByType(articleTypeId));
        return dtoList;
    }

    public List<ArticleDTO> findLastThreeArticleByType(Integer articleTypeId) {
        List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLastThreeArticleByType(articleTypeId));
        return dtoList;
    }

    public List<ArticleDTO> convertToDTO(List<ArticleEntity> entityList) {
        List<ArticleDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(entity.getId());
            dto.setDescription(entity.getDescription());
            dto.setContent(entity.getContent());
            dto.setTitle(entity.getTitle());
            dto.setStatus(entity.getStatus());
            dto.setArticleTypeId(entity.getType().getId());
            dto.setVisible(entity.getVisible());
            dto.setCategoryId(entity.getCategory().getId());
            dto.setModeratorId(entity.getModerator().getId());
            dto.setSharedCount(entity.getSharedCount());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setRegionId(entity.getRegion().getId());
            dto.setViewCount(entity.getViewCount());
            dto.setPublisherId(entity.getPublisher().getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        });
        return dtoList;
    }

//    public List<ArticleEntity> convertToEntity(List<ArticleDTO> dtoList) {
//        List<ArticleEntity> entityList = new LinkedList<>();
//        dtoList.forEach(dto -> {
//            ArticleEntity entity = new ArticleEntity();
//        });
//        return null;
//    }

}
