package com.example.controller;

import com.example.dto.articletype.ArticleTypeDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleTypeConrtoller {
    @Autowired
    private ArticleTypeService articleService;

    @PostMapping({"", "/"})
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody ArticleTypeDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.update(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<ArticleTypeDTO>> paging(@RequestHeader("Authorization") String authorization,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<ArticleTypeDTO> response = articleService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") Integer id) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }
    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang,
                                                  @RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        List<String> list = articleService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
