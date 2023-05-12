package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.dto.region.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping({"/private", "/private/"})
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto) {

        return ResponseEntity.ok(regionService.create(dto, SpringSecurityUtil.getProfileId()));
    }

    @PutMapping(value = "private/update")
    public ResponseEntity<?> update(@RequestBody RegionDTO dto) {

        return ResponseEntity.ok(regionService.update(dto, SpringSecurityUtil.getProfileId()));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<RegionDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        Page<RegionDTO> response = regionService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(SpringSecurityUtil.getProfileId(), id));
    }

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang) {
               List<String> list = regionService.getByLang(lang);
        return ResponseEntity.ok(list);
    }
}
