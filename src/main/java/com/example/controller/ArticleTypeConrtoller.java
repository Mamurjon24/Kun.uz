package com.example.controller;

import com.example.dto.articletype.ArticleTypeDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articleType")
public class ArticleTypeConrtoller {
    @Autowired
    private ArticleTypeService articleService;

    @PostMapping({"/private", "/private/"})
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto,
                                                 HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(articleService.create(dto, ptrId));
    }

    @PutMapping(value = "/private/update")
    public ResponseEntity<?> update(@RequestBody ArticleTypeDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(articleService.update(dto, ptrId));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<ArticleTypeDTO>> paging(HttpServletRequest request,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Page<ArticleTypeDTO> response = articleService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/private/delete/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @PathVariable("id") Integer id) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer ptrId = (Integer) request.getAttribute("role");
        return ResponseEntity.ok(articleService.delete(ptrId,id));
    }

    @PutMapping(value = "/private/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang,
                                                  HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        List<String> list = articleService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
