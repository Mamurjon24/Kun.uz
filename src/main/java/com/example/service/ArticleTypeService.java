package com.example.service;

import com.example.dto.articletype.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.enums.LangEnum;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.repository.ArticleTypeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        isValidProfile(dto);
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        articleTypeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findByNameUzAndNameRuAndNameEng(dto.getNameUz(), dto.getNameRu(),dto.getNameEng());
        if (optional.isPresent()) {
            throw new MethodNotAllowedExeption("This ArticleType Name already use :)");
        }
    }

    public Boolean update(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = get(dto.getId());
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        articleTypeRepository.save(entity);
        return true;
    }
    public ArticleTypeEntity get(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Article not found: " + id);
        }
        return optional.get();
    }
    public Page<ArticleTypeDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ArticleTypeEntity> entityList = pageObj.getContent();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setVisible(entity.getVisible());
            dto.setUpdatedDate(entity.getUpdatedDate());
            dto.setNameEng(entity.getNameEng());
            dto.setNameRu(entity.getNameRu());
            dto.setNameUz(entity.getNameUz());
            dtoList.add(dto);
        }
        Page<ArticleTypeDTO> response = new PageImpl<ArticleTypeDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public Integer delete(Integer id) {
        Integer num = articleTypeRepository.changeArticleVisible(Boolean.FALSE,SpringSecurityUtil.getProfileId(),id );
        return num;
    }

    public List<String> getByLang(String lang) {
        switch (lang){
            case "Uz":
                List<String> listUz = articleTypeRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "Ru":
                List<String> listRu = articleTypeRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "Eng":
                List<String> listEng = articleTypeRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
    public ArticleTypeDTO getByIdAndLang(Integer id, LangEnum lang) {
        ArticleTypeEntity entity = get(id);
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        switch (lang) {
            case ENG -> {
                dto.setName(entity.getNameEng());
            }
            case RU -> {
                dto.setName(entity.getNameRu());
            }
            case UZ -> {
                dto.setName(entity.getNameUz());
            }
        }
        return dto;
    }
}
