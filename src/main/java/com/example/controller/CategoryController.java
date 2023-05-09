package com.example.controller;

import com.example.dto.category.CategoryDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.dto.region.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping({"/private/", "/private"})
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(categoryService.create(dto, ptrId));
    }

    @PutMapping(value = "private/update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(categoryService.update(dto, ptrId));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<CategoryDTO>> paging(HttpServletRequest request,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<CategoryDTO> response = categoryService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "private/delete/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @PathVariable("id") Integer id) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(categoryService.delete(ptrId, id));
    }

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang,
                                                  @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        List<String> list = categoryService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
