package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.dto.savedArticle.SavedArticleCreateRequestDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.SavedArticleMapper;
import com.example.service.SavedArticleService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping({"", "/"})
    public ResponseEntity<?> create(@RequestBody SavedArticleCreateRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(savedArticleService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer commentId) {
        return ResponseEntity.ok(savedArticleService.delete(commentId));
    }
    @PostMapping(value = "/getList")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String authorization) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        SavedArticleMapper list = savedArticleService.getAllSavedArticle();
        if (list == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
