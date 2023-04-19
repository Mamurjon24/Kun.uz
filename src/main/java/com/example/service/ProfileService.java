package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto, Integer adminId) {
        // check - homework
        isValidProfile(dto);
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPrtId(adminId);
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity); // save profile
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
       if (dto.getRole().equals(ProfileRole.ADMIN)){
           throw new MethodNotAllowedExeption("You cannot Create Admin:)");
       }

    }

    public boolean update(Integer adminId, ProfileDTO dto) {
        ProfileEntity entity = get(dto.getId());
        if (dto.getRole().equals(ProfileRole.ADMIN)){
            throw new MethodNotAllowedExeption("You Cannot update Profiles By ADMINE status");
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setVisible(dto.getVisible());
        entity.setPrtId(adminId);
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return true;
    }

    public List<ProfileDTO> getAll() {
        List<ProfileDTO> dtoList = getdtoList(profileRepository.findAll());
        return dtoList;
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = get(id);
        ProfileDTO dto = convertToDTO(entity);
        return dto;
    }

    public ProfileEntity get(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Student not found: " + id);
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
