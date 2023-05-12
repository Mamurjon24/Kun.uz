package com.example.controller;

import com.example.dto.comment.CommentCreateRequestDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.entity.CommentEntity;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleCommentMapper;
import com.example.mapper.ArticleCommentPagenationMapper;
import com.example.service.CommentService;
import com.example.util.JwtUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.update(dto));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer commentId) {
        CommentEntity comment = commentService.get(commentId);
        boolean request = false;
        if (comment.getProfileId().equals(SpringSecurityUtil.getProfileId())) {
            commentService.delete(commentId);
            request = true;
        }
        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/getListByArticleId/{id}")
    public ResponseEntity<?> getList(@PathVariable("id") String articleId) {
        List<ArticleCommentMapper> list = commentService.getAllByArticleId(articleId);
        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/public/paging")
    public ResponseEntity<Page<ArticleCommentPagenationMapper>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        Page<ArticleCommentPagenationMapper> response = commentService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/findRepliedCommentListByCommentId/{id}")
    public ResponseEntity<?> findRepliedCommentListByCommentId(@PathVariable("id") Integer articleId) {
            List<ArticleCommentMapper> list = commentService.getRepliedCommentListByCommentId(articleId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<CommentDTO>> filter(@RequestBody CommentFilterDTO dto,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.filter(dto, page, size));
    }

}
