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
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PutMapping(value = "/private/update")
    public ResponseEntity<?> update(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleService.update(dto));
    }

    @PutMapping(value = "/private/paging")
    public ResponseEntity<Page<ArticleTypeDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        Page<ArticleTypeDTO> response = articleService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping(value = "/private/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang) {
        List<String> list = articleService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
