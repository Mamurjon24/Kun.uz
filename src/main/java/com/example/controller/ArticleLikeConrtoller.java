package com.example.controller;

import com.example.dto.articleLike.ArticleLikeDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleLikeService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article-like")
public class ArticleLikeConrtoller {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping({"", "/"})
    public ResponseEntity<?> create(@RequestBody @Valid ArticleLikeDTO dto) {
              return ResponseEntity.ok(articleLikeService.check(dto));
    }
}
