package com.example.controller;

import com.example.dto.category.CategoryDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.dto.region.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping({"", "/"})
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<CategoryDTO>> paging(@RequestHeader("Authorization") String authorization,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<CategoryDTO> response = categoryService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") Integer id) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang,
                                                  @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        List<String> list = categoryService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
