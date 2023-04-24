package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleTypeService articleTypeService;

    public ArticleDTO create(ArticleDTO dto, Integer id) {
        isValidProfile(dto);
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getSharedCount());
        entity.setCategory(categoryService.get(dto.getCategoryId()));
        entity.setRegion(regionService.get(dto.getRegionId()));
        entity.setModerator(profileService.get(id));
        entity.setArticleType(articleTypeService.get(dto.getArticleTypeId()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Boolean.FALSE);
        entity.setStatus(ArticleStatus.NOTPUBLISHED);
        articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ArticleDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.findAllByTitle(dto.getTitle());
        if (optional.isPresent()) {
            throw new MethodNotAllowedExeption("This Article already use :)");
        }
    }

    public boolean update(ArticleDTO dto, Integer id) {
        ArticleEntity entity = get(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getSharedCount());
        entity.setCategory(categoryService.get(dto.getCategoryId()));
        entity.setRegion(regionService.get(dto.getRegionId()));
        entity.setModerator(profileService.get(id));
        entity.setArticleType(articleTypeService.get(dto.getArticleTypeId()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Boolean.FALSE);
        entity.setStatus(ArticleStatus.NOTPUBLISHED);
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

    public Boolean delete(String id) {
        ArticleEntity entity = get(id);
        if (entity == null) {
            throw new MethodNotAllowedExeption("Article not found:)");
        }
        articleRepository.changeVisible(Boolean.FALSE, ArticleStatus.NOTPUBLISHED, id);
        return true;
    }

    public Boolean changeStatus(String id, Integer publisherId) {
        ArticleEntity entity = get(id);
        if (entity == null) {
            throw new MethodNotAllowedExeption("Article not found:)");
        }
        entity.setPublisher(profileService.get(publisherId));
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
    public List<ArticleDTO> convertToDTO(List<ArticleEntity> entityList){
        List<ArticleDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(entity.getId());
            dto.setDescription(entity.getDescription());
            dto.setContent(entity.getContent());
            dto.setTitle(entity.getTitle());
            dto.setStatus(entity.getStatus());
            dto.setArticleTypeId(entity.getArticleType().getId());
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
    public List<ArticleEntity> convertToEntity(List<ArticleDTO> dtoList){
        List<ArticleEntity> entityList = new LinkedList<>();
        dtoList.forEach(dto ->{
            ArticleEntity entity = new ArticleEntity();
        });
        return null;
    }

}
