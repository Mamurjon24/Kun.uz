package com.example.service;

import com.example.dto.comment.CommentCreateRequestDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.tag.TagDTO;
import com.example.entity.CommentEntity;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.exp.MethodNotAllowedExeption;
import com.example.mapper.ArticleCommentMapper;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public String create(CommentCreateRequestDTO dto, Integer profileId) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        commentRepository.save(entity);
        return "Comment Created";
    }

    public Boolean update(Integer profileId, CommentDTO dto) {
        CommentEntity entity = commentRepository.findByIdAndProfileId(dto.getArticleId(), profileId);
        if (entity == null) {
            throw new ItemNotFoundException("Not found:)");
        }
        entity.setProfileId(profileId);
        entity.setArticleId(dto.getArticleId());
        entity.setContent(dto.getContent());
        entity.setUpdatedDate(dto.getUpdateDate());
        commentRepository.save(entity);
        return true;
    }

    public CommentEntity get(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Comment not found: " + id);
        }
        return optional.get();
    }

    public Boolean delete(Integer commentId) {
        commentRepository.changeVisible(commentId);
        return true;
    }

    public ArticleCommentMapper getAllByArticleId(String articleId) {
        ArticleCommentMapper dtoList = commentRepository.findByArticleId(articleId);
        return dtoList;
    }


}
