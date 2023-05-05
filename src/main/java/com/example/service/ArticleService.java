package com.example.service;

import com.example.dto.article.*;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.LangEnum;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.article.ArticleCustomRepository;
import com.example.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    private AttachService attachService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleCustomRepository articleCustomRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArticleTypeService articleTypeService;

    public ArticleRequestDTO create(ArticleRequestDTO dto, Integer moderId) {
        // check
//        ProfileEntity moderator = profileService.get(moderId);
//        RegionEntity region = regionService.get(dto.getRegionId());
//        CategoryEntity category = categoryService.get(dto.getCategoryId());
        Optional<ArticleEntity> optional = articleRepository.findByTitle(dto.getTitle());
        if (optional.isPresent()) {
            throw new ItemNotFoundException(" This Title is already use bro! :)");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderId);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setTypeId(dto.getTypeId());
        entity.setStatus(ArticleStatus.NOTPUBLISHED);
        entity.setImageId(dto.getImageId());
        articleRepository.save(entity);
        return dto;
    }

    public boolean update(ArticleUpdateRequestDTO dto, Integer id) {
        Optional<ArticleEntity> optional = articleRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException(" Title must be in Database :)");
        }
        ArticleEntity entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(id);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setTypeId(dto.getTypeId());
        entity.setImageId(dto.getImageId());
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

    public Integer delete(Integer modetorId, String articleId) {
        get(articleId);
        Integer response = articleRepository.changeVisible(Boolean.FALSE, modetorId, articleId);
        return response;
    }

    /*
      public Boolean changeStatus(String id, Integer publisherId) {
           ArticleEntity entity = get(id);
           entity.setPublisherId(publisherId);
           entity.setPublishedDate(LocalDateTime.now());
           entity.setStatus(ArticleStatus.PUBLISHED);
           articleRepository.changeStatus(Boolean.TRUE, ArticleStatus.PUBLISHED, id);
           return true;
       }
       */
    public Boolean changeStatus(ArticleStatus status, String id, Integer prtId) {
        ArticleEntity entity = get(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(prtId);
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        // articleRepository.changeStatus(status, id);
        return true;
    }

    //    public List<ArticleDTO> findLastFiveArticleByType(Integer articleTypeId) {
//        List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLastFiveArticleByType(articleTypeId));
//        return dtoList;
//    }
    public List<ArticleShortInfoDTO> getLast5ByTypeId(Integer typeId) {
        List<ArticleEntity> entityList = articleRepository.findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(typeId,
                ArticleStatus.PUBLISHED, true);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public List<ArticleDTO> findLastThreeArticleByType(Integer articleTypeId) {
        List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLastThreeArticleByType(articleTypeId));
        return dtoList;
    }

    public List<ArticleShortInfoDTO> findLast4ArticleByTypes(List<String> idList, Integer typeId) {
        // List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLast4ArticleByTypes(typeId,idList));
        List<ArticleShortInfoMapper> entityList = articleRepository.findLast4ArticleByTypes(typeId, idList);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public List<ArticleShortInfoDTO> findArticlesNotInList(List<String> idListParam) {
        //List<ArticleDTO> dtoList = convertToDTO(articleRepository.findLast4ArticleByTypes(typeId,idListParam));
        List<ArticleShortInfoMapper> entityList = articleRepository.findArticlesNotInList(idListParam);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public ArticleFullInfoDTO getById(String id, LangEnum langEnum) {
        ArticleEntity entity = get(id);
        if (!entity.getVisible() || !entity.getStatus().equals(ArticleStatus.PUBLISHED)) {
            throw new AppBadRequestException("Wrong article status");
        }
        return toFullDTO(entity, langEnum);
    }

    public List<ArticleShortInfoDTO> find4MostReadArticles() {
        List<ArticleShortInfoMapper> entityList = articleRepository.findTop4ByViewCountAndStatusAndVisible();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }
    public List<ArticleShortInfoDTO> findByTags(String tags) {
        List<ArticleShortInfoMapper> entityList = articleRepository.findByTags(tags);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public List<ArticleShortInfoDTO> findLast5ArticleByTypesAndRegionId(Integer regionId, String typeName) {
        List<ArticleShortInfoMapper> entityList = articleRepository.findTop5ByArticleTypeAndRegionId(typeName, regionId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public Page<ArticleDTO> pagingtionByRegion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "region");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleEntity> pageObj = articleRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ArticleEntity> entityList = pageObj.getContent();
        List<ArticleDTO> dtoList = convertToDTO(entityList);
        Page<ArticleDTO> response = new PageImpl<ArticleDTO>(dtoList, pageable, totalCount);
        return response;
    }

    public List<ArticleShortInfoDTO> findTop5ByArticleByCategory(Integer categoryId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.findTop5ByArticleByCategory(categoryId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public Page<ArticleShortInfoDTO> pagingtionByCategory(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "category");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleEntity> pageObj = articleRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ArticleEntity> entityList = pageObj.getContent();
        List<ArticleShortInfoDTO> dtoList = toArticleShortInfoList(entityList);
        Page<ArticleShortInfoDTO> response = new PageImpl<ArticleShortInfoDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public Boolean increaseArticleViewCountByArticleId(String articleId){
        Integer request = articleRepository.increaseArticleViewCountByArticleId(articleId);
        return  request > 0;
    }
    public Boolean increaseArticleShareCountByArticleId(String articleId){
        Integer request = articleRepository.increaseArticleShareCountByArticleId(articleId);
        return  request > 0;
    }
    public PageImpl<ArticleShortInfoDTO> filter(ArticleFilterDTO filterDTO, int page, int size) {
        Page<ArticleEntity> pageObj = articleCustomRepository.filter(filterDTO, page, size);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        pageObj.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return new PageImpl<>(dtoList, PageRequest.of(page, size), pageObj.getTotalElements());
    }
    public List<ArticleShortInfoDTO> toArticleShortInfoList(List<ArticleEntity> entityList) {
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        // dto.setImage(attachService.getAttachLink(entity.getPhotoId()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    public ArticleShortInfoDTO toArticleShortInfo(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        // dto.setImage(attachService.getAttachLink(entity.getImageId()));
        return dto;
    }

    public ArticleShortInfoDTO toArticleShortInfo(ArticleShortInfoMapper entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        // dto.setImage(attachService.getAttachLink(entity.getAttachId()));
        return dto;
    }

    public ArticleFullInfoDTO toFullDTO(ArticleEntity entity, LangEnum langEnum) {
        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setImage(attachService.getAttachLink(entity.getImageId()));
        dto.setCategory(categoryService.getByIdAndLang(entity.getCategoryId(), langEnum));
        dto.setRegion(regionService.getByIdAndLang(entity.getCategoryId(), langEnum));
        dto.setArticleType(articleTypeService.getByIdAndLang(entity.getCategoryId(), langEnum));
        // tag_list
        return dto;
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
            dto.setCategoryId(entity.getCategoryId());
            dto.setModeratorId(entity.getModeratorId());
            dto.setSharedCount(entity.getSharedCount());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setRegionId(entity.getRegionId());
            dto.setViewCount(entity.getViewCount());
            dto.setImageId(entity.getImageId());
            dto.setPublisherId(entity.getPublisherId());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
