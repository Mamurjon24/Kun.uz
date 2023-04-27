package com.example.service;

import com.example.dto.email.EmailDTO;
import com.example.entity.EmailEntity;
import com.example.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    public List<EmailDTO> findByEmail(String email) {
        List<EmailDTO> dtoList = convertToDTO(emailRepository.findByEmail(email));
        return dtoList;
    }
    public List<EmailDTO> findByCreatedData(LocalDate createdData) {
        List<EmailDTO> dtoList = convertToDTO(emailRepository.findByCreatedDataBetween(createdData.atStartOfDay(), LocalDateTime.of(createdData, LocalTime.MAX)));
        return dtoList;
    }
    public Page<EmailDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdData");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailEntity> pageObj = emailRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<EmailEntity> entityList = pageObj.getContent();
        List<EmailDTO> dtoList = convertToDTO(entityList);
        Page<EmailDTO> response = new PageImpl<EmailDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public List<EmailDTO> convertToDTO(List<EmailEntity> entityList) {
        List<EmailDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            EmailDTO dto = new EmailDTO();
            dto.setEmail(entity.getEmail());
            dto.setId(entity.getId());
            dto.setMessage(entity.getMessage());
            dto.setCreatedData(entity.getCreatedData());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
