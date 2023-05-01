package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleRequestDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.dto.article.ArticleUpdateRequestDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping({"", "/"})
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody @Valid ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody ArticleUpdateRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.update(dto, jwt.getId()));
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") String id) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(jwtDTO.getId(), id));
    }

    @PutMapping(value = "/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@RequestHeader("Authorization") String authorization,
                                          @RequestParam("status") String status,
                                          @PathVariable("id") String id) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id, jwtDTO.getId()));
    }

    //    @GetMapping(value = "/getLastFiveArticleByType/{id}")
//    public ResponseEntity<List<ArticleDTO>> getLastFiveArticleByType(@PathVariable("id") Integer articleId) {
//        List<ArticleDTO> list = articleService.findLastFiveArticleByType(articleId);
//        return ResponseEntity.ok(list);
//    }
    @GetMapping(value = "/getLastThreeArticleByType/{id}")
    public ResponseEntity<List<ArticleDTO>> getLastThreeArticleByType(@PathVariable("id") Integer articleId) {
        List<ArticleDTO> list = articleService.findLastThreeArticleByType(articleId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getArticlesNotInList")
    public ResponseEntity<List<ArticleShortInfoDTO>> getArticlesNotInList(@RequestParam String[] idList) {
        List<String> idListParam = Arrays.asList(idList);
        List<ArticleShortInfoDTO> articleList = articleService.findArticlesNotInList(idListParam);
        return ResponseEntity.ok(articleList);
    }

    @PutMapping("/articles/getLast4Articles/{id}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4Articles(@RequestBody List<String> articleIdList,
                                                                      @PathVariable("id") Integer articleId) {
        List<ArticleShortInfoDTO> articleList = articleService.findLast4ArticleByTypes(articleIdList, articleId);
        return ResponseEntity.ok(articleList);
    }



}
