package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping({"", "/"})
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }

    @PutMapping (value = "/update")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto,jwtDTO.getId()));
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") String id) {
        JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping(value = "/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@RequestHeader("Authorization") String authorization,
                                          @PathVariable("id") String id) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(id,jwtDTO.getId()));
    }
    @GetMapping(value = "/getLastFiveArticleByType/{id}")
    public ResponseEntity<List<ArticleDTO>> getLastFiveArticleByType(@PathVariable("id") Integer articleId) {
        List<ArticleDTO> list = articleService.findLastFiveArticleByType(articleId);
        return ResponseEntity.ok(list);
    }
    @GetMapping(value = "/getLastThreeArticleByType/{id}")
    public ResponseEntity<List<ArticleDTO>> getLastThreeArticleByType(@PathVariable("id") Integer articleId) {
        List<ArticleDTO> list = articleService.findLastThreeArticleByType(articleId);
        return ResponseEntity.ok(list);
    }

}
