package com.example.service;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileRequestCustomDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.repository.profile.ProfileCustomRepository;
import com.example.repository.profile.ProfileRepository;
import com.example.util.MD5Util;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(ProfileDTO dto) {
        // check - homework
        isValidProfile(dto);
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity); // save profile
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (!optional.isEmpty()) {
            throw new MethodNotAllowedExeption("This email or password already use :)");
        }
        if (dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new MethodNotAllowedExeption("You cannot Create Admin:)");
        }
    }

    public boolean update(ProfileDTO dto) {
        ProfileEntity entity = get(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setVisible(dto.getVisible());
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return true;
    }

    public boolean updateOwnProfile(ProfileDTO dto) {
        ProfileEntity entity = get(SpringSecurityUtil.getProfileId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        profileRepository.save(entity);
        return true;
    }

    public List<ProfileDTO> getAll() {
        List<ProfileDTO> dtoList = getdtoList(profileRepository.findAll());
        return dtoList;
    }

    public Page<ProfileDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<ProfileEntity> entityList = pageObj.getContent();
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPhone(entity.getPhone());
            dto.setPassword(entity.getPassword());
            dto.setRole(entity.getRole());
            dto.setSurname(entity.getSurname());
            dto.setStatus(entity.getStatus());
            dto.setEmail(entity.getEmail());
            dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        }
        Page<ProfileDTO> response = new PageImpl<ProfileDTO>(dtoList, pageable, totalCount);
        return response;
    }

    public Integer delete(Integer id) {
        get(id);
        Integer num = profileRepository.changeVisible(Boolean.FALSE, GeneralStatus.BLOCK,SpringSecurityUtil.getProfileId(), id);
        return num;
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = get(id);
        ProfileDTO dto = convertToDTO(entity);
        return dto;
    }

    public ProfileEntity get(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Profile not found: " + id);
        }
        return optional.get();
    }

    public List<ProfileDTO> getdtoList(Iterable<ProfileEntity> dtos) {
        List<ProfileDTO> dtoList = new LinkedList<>();
        dtos.forEach(entity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setRole(entity.getRole());
            dto.setPassword(entity.getPassword());
            dto.setSurname(entity.getSurname());
            dto.setPhone(entity.getPhone());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public Boolean updatePhoto(String photoId) {
        ProfileEntity entity = get(SpringSecurityUtil.getProfileId());
        entity.setPhotoId(photoId);
        profileRepository.save(entity);
        return true;
    }

    public void filter(ProfileRequestCustomDTO filterDTO) {
        List<ProfileEntity> list = profileCustomRepository.filter(filterDTO);
        System.out.println(list);
    }

    public ProfileDTO convertToDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setPassword(entity.getPassword());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        return dto;
    }

   /*
    public ProfileEntity convertToEntity(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        //entity.setPrtId(adminId);
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        return entity;
    }
    */


}
