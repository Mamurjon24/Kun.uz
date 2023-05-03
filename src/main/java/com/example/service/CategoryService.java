package com.example.service;

import com.example.dto.category.CategoryDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LangEnum;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CategoryDTO dto, Integer id) {
        isValidProfile(dto);
        CategoryEntity entity = new CategoryEntity();
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(id);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(CategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByNameUzAndNameRuAndNameEng(dto.getNameUz(), dto.getNameRu(),dto.getNameEng());
        if (optional.isPresent()) {
            throw new MethodNotAllowedExeption("This Category Name already use :)");
        }
    }

    public Boolean update(CategoryDTO dto, Integer id) {
        CategoryEntity entity = get(dto.getId());
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(id);
        categoryRepository.save(entity);
        return true;
    }
    public CategoryEntity get(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Category not found: " + id);
        }
        return optional.get();
    }
    public Page<CategoryDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<CategoryEntity> entityList = pageObj.getContent();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : entityList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setId(entity.getId());
            dto.setVisible(entity.getVisible());
            dto.setUpdatedDate(entity.getUpdatedDate());
            dto.setNameEng(entity.getNameEng());
            dto.setNameRu(entity.getNameRu());
            dto.setNameUz(entity.getNameUz());
            dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        }
        Page<CategoryDTO> response = new PageImpl<CategoryDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public Integer delete(Integer adminId,Integer id) {
        Integer num = categoryRepository.changeRegionVisible(Boolean.FALSE,adminId,id );
        return num;
    }

    public List<String> getByLang(String lang) {
        switch (lang){
            case "Uz":
                List<String> listUz = categoryRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "Ru":
                List<String> listRu = categoryRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "Eng":
                List<String> listEng = categoryRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
    public CategoryDTO getByIdAndLang(Integer id, LangEnum lang) {
        CategoryEntity entity = get(id);
        CategoryDTO dto = new CategoryDTO();
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
