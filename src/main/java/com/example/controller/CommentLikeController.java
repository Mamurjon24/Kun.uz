package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.service.CommentLikeService;
import com.example.util.JwtUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") Integer commentId) {
        return ResponseEntity.ok(commentLikeService.like(commentId, SpringSecurityUtil.getProfileId()));
    }

    @GetMapping("/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") Integer commentId) {

        return ResponseEntity.ok(commentLikeService.dislike(commentId, SpringSecurityUtil.getProfileId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer commentId) {
        return ResponseEntity.ok(commentLikeService.delete(commentId, SpringSecurityUtil.getProfileId()));
    }

}
