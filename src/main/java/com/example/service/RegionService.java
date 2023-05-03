package com.example.service;

import com.example.dto.region.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.LangEnum;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    public RegionDTO create(RegionDTO dto, Integer id) {
        isValidProfile(dto);
        RegionEntity entity = new RegionEntity();
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(id);
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findByNameUzAndNameRuAndNameEng(dto.getNameUz(), dto.getNameRu(),dto.getNameEng());
        if (optional.isPresent()) {
            throw new MethodNotAllowedExeption("This Name already use :)");
        }
    }

    public Boolean update(RegionDTO dto, Integer id) {
        RegionEntity entity = get(dto.getId());
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(id);
        regionRepository.save(entity);
        return true;
    }
    public RegionEntity get(Integer id) {
        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Article not found: " + id);
        }
        return optional.get();
    }
    public Page<RegionDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<RegionEntity> entityList = pageObj.getContent();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            RegionDTO dto = new RegionDTO();
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
        Page<RegionDTO> response = new PageImpl<RegionDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public Integer delete(Integer adminId,Integer id) {
        Integer num = regionRepository.changeRegionVisible(Boolean.FALSE,adminId,id );
        return num;
    }

    public List<String> getByLang(String lang) {
        switch (lang){
            case "Uz":
                List<String> listUz = regionRepository.findAllByNameUzOrderByCreatedDate();
                return listUz;
            case "Ru":
                List<String> listRu = regionRepository.findAllByNameRuOrderByCreatedDate();
                return listRu;
            case "Eng":
                List<String> listEng = regionRepository.findAllByNameEngOrderByCreatedDate();
                return listEng;
        }
        return null;
    }
    public RegionDTO getByIdAndLang(Integer id, LangEnum lang) {
        RegionEntity entity = get(id);
        RegionDTO dto = new RegionDTO();
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
