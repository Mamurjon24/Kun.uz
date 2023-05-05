package com.example.controller;

import com.example.dto.article.*;
import com.example.dto.jwt.JwtDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.LangEnum;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody ArticleUpdateRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.update(dto, jwt.getId()));
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") String id) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTOForArticle(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(jwtDTO.getId(), id));
    }

    @PutMapping(value = "/change-Status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam("status") String status,
                                          @RequestHeader("Authorization") String authorization) {
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

    @PutMapping("/findLast4ArticleByTypes/{id}")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4Articles(@RequestBody ArticleIdListDTO articleIdList,
                                                                      @PathVariable("id") Integer articleId) {
        List<ArticleShortInfoDTO> articleList = articleService.findLast4ArticleByTypes(articleIdList.getArticleIdList(), articleId);
        return ResponseEntity.ok(articleList);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ArticleFullInfoDTO> getById(@PathVariable("id") String id,
                                                      @RequestHeader(value = "Accept-Language", defaultValue = "UZ", required = false) LangEnum lang) {
        return ResponseEntity.ok(articleService.getById(id, lang));
    }

    @GetMapping("/get4MostReadArticles")
    public ResponseEntity<List<ArticleShortInfoDTO>> get4MostReadArticles() {
        List<ArticleShortInfoDTO> articleList = articleService.find4MostReadArticles();
        return ResponseEntity.ok(articleList);
    }
    @GetMapping("/getByTags")
    public ResponseEntity<List<ArticleShortInfoDTO>> getByTags(@RequestParam String tag) {
        List<ArticleShortInfoDTO> articleList = articleService.findByTags(tag);
        return ResponseEntity.ok(articleList);
    }

    @PutMapping(value = "/findLast5ArticleByTypesAndRegionId/{id}")
    public ResponseEntity<?> findLast5ArticleByTypesAndRegionId(@PathVariable("id") Integer regionId,
                                                                @RequestParam("typeName") String typeName) {
        List<ArticleShortInfoDTO> articleList = articleService.findLast5ArticleByTypesAndRegionId(regionId, typeName);
        return ResponseEntity.ok(articleList);
    }

    @PutMapping(value = "/pagingByRegion")
    public ResponseEntity<Page<ArticleDTO>> pagingByRegion(@RequestHeader("Authorization") String authorization,
                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<ArticleDTO> response = articleService.pagingtionByRegion(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/getTop5ByArticleByCategory/{id}")
    public ResponseEntity<?> getTop5ByArticleByCategory(@PathVariable("id") Integer regionId) {
        List<ArticleShortInfoDTO> articleList = articleService.findTop5ByArticleByCategory(regionId);
        return ResponseEntity.ok(articleList);
    }

    @PutMapping(value = "/pagingByCategory")
    public ResponseEntity<Page<ArticleShortInfoDTO>> pagingByCategory(@RequestHeader("Authorization") String authorization,
                                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                                      @RequestParam(value = "size", defaultValue = "2") int size) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        Page<ArticleShortInfoDTO> response = articleService.pagingtionByCategory(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/getIncreaseArticleViewCountByArticleId/{id}")
    public ResponseEntity<?> getIncreaseArticleViewCountByArticleId(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseArticleViewCountByArticleId(articleId));
    }

    @PutMapping(value = "/getIncreaseArticleShareCountByArticleId/{id}")
    public ResponseEntity<?> getIncreaseArticleShareCountByArticleId(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseArticleShareCountByArticleId(articleId));
    }
    @PostMapping("/filter")
    public ResponseEntity<Page<ArticleShortInfoDTO>> filter(@RequestBody ArticleFilterDTO dto,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.filter(dto, page, size));
    }


}
