package com.example.service;

import com.example.dto.auth.AuthDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.dto.register.RegistrationDTO;
import com.example.dto.register.RegistrationResponseDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;

    public AuthResponseDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadRequestException("Wrong status");
        }
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return responseDTO;
    }
    /*
    public ProfileDTO register(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if (optional.isPresent()) {
            throw new ItemNotFoundException("This email or password already use :)");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setPrtId(1);
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setRole(ProfileRole.ADMIN);
        profileRepository.save(entity);
        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }
    */
    public RegistrationResponseDTO registration(RegistrationDTO dto) {
        // check -?
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new ItemNotFoundException("Email already exists mazgi.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRole.USER);
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTER);
        entity.setVisible(Boolean.FALSE);
        mailSenderService.sendRegistrationEmail(dto.getEmail());
        profileRepository.save(entity);
        String s = "Verification link was send to email: " + dto.getEmail();
        return new RegistrationResponseDTO(s);
    }
    public RegistrationResponseDTO emailVerification(String jwt) {
        // asjkdhaksdh.daskhdkashkdja
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.REGISTER)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(GeneralStatus.ACTIVE);
        profileRepository.save(entity);
        return new RegistrationResponseDTO("Registration Done");
    }
}
