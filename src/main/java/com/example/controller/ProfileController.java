package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileRequestCustomDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping({"", "/"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(profileService.create(dto, ptrId));
    }

    @PutMapping(value = "/private/update")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody ProfileDTO dto) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(profileService.update(ptrId, dto));
    }

    @PutMapping(value = "/public/updateByProfile")
    public ResponseEntity<?> updateByProfile(@RequestHeader("Authorization") String authorization,
                                             @RequestBody ProfileDTO dto) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(profileService.updateOwnProfile(jwtDTO.getId(), dto));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<ProfileDTO>> paging(HttpServletRequest request,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<ProfileDTO> response = profileService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/getProfileList")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        List<ProfileDTO> list = profileService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable("id") Integer id) {
        ProfileDTO dto = profileService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/private/delete/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @PathVariable("id") Integer id) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(profileService.delete(ptrId,id));
    }

    @PutMapping(value = "/updatePhoto/{photoId}")
    public ResponseEntity<?> updatePhoto(@RequestHeader("Authorization") String authorization,
                                         @PathVariable("photoId") String photoId) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(profileService.updatePhoto(jwtDTO.getId(),photoId));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileRequestCustomDTO filterDTO) {
        profileService.filter(filterDTO);
        return ResponseEntity.ok().build();
    }


}
