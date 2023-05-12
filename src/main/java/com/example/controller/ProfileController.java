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

    @PostMapping({"/adm", "/adm/"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping(value = "/private/update")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.update(dto));
    }

    @PutMapping(value = "/public/updateByProfile")
    public ResponseEntity<?> updateByProfile(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.updateOwnProfile(dto));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<ProfileDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "2") int size) {
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
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PutMapping(value = "/updatePhoto/{photoId}")
    public ResponseEntity<?> updatePhoto(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok(profileService.updatePhoto(photoId));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileRequestCustomDTO filterDTO) {
        profileService.filter(filterDTO);
        return ResponseEntity.ok().build();
    }


}
