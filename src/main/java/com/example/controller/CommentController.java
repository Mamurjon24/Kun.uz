package com.example.controller;

import com.example.dto.comment.CommentCreateRequestDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.entity.CommentEntity;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleCommentMapper;
import com.example.service.CommentService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping({"", "/"})
    public ResponseEntity<?> create(@RequestBody CommentCreateRequestDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return ResponseEntity.ok(commentService.update(jwtDTO.getId(), dto));
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization,
                                    @PathVariable("id") Integer commentId) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        CommentEntity comment = commentService.get(commentId);
        boolean request = false;
        if (comment.getProfileId().equals(jwtDTO.getId()) || JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN) != null){
            commentService.delete(commentId);
            request = true;
        }
        return ResponseEntity.ok(request);
    }
    @PostMapping(value = "/getList/{id}")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String authorization,
                                     @PathVariable("id") String articleId) {
        JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        List<ArticleCommentMapper> list = commentService.getAllByArticleId(articleId);
        return ResponseEntity.ok(list);
    }

}
