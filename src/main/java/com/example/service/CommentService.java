package com.example.service;

import com.example.dto.article.ArticleFilterDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.dto.comment.CommentCreateRequestDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleCommentMapper;
import com.example.mapper.ArticleCommentPagenationMapper;
import com.example.repository.comment.CommentCustomRepository;
import com.example.repository.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentCustomRepository commentCustomRepository;

    public String create(CommentCreateRequestDTO dto, Integer profileId) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        entity.setReplyId(dto.getReplyId());
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
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setReplyId(dto.getReplyId());
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

    public List<ArticleCommentMapper> getAllByArticleId(String articleId) {
        List<ArticleCommentMapper> dtoList = commentRepository.findByArticleId(articleId);
        return dtoList;
    }

    public Page<ArticleCommentPagenationMapper> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<CommentEntity> pageObj = commentRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<CommentEntity> entityList = pageObj.getContent();
        List<ArticleCommentPagenationMapper> dtoList = new LinkedList<>();
        for (CommentEntity entity : entityList) {
            ArticleCommentPagenationMapper dto = new ArticleCommentPagenationMapper();
            dto.setId(entity.getId());
            dto.setArticleId(entity.getArticleId());
            dto.setProfileId(entity.getProfileId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setContent(entity.getContent());
            dto.setReplyId(entity.getReplyId());
            dto.setUpdateDate(entity.getUpdatedDate());
            dto.setArticleTitle(entity.getArticle().getTitle());
            dto.setProfileName(entity.getProfile().getName());
            dto.setProfileSurname(entity.getProfile().getSurname());
            dtoList.add(dto);
        }
        Page<ArticleCommentPagenationMapper> response = new PageImpl<ArticleCommentPagenationMapper>(dtoList, pageable, totalCount);
        return response;
    }

    public List<ArticleCommentMapper> getRepliedCommentListByCommentId(Integer replyId) {
        List<ArticleCommentMapper> dtoList = commentRepository.getRepliedCommentListByCommentId(replyId);
        return dtoList;
    }


    public PageImpl<CommentDTO> filter(CommentFilterDTO filterDTO, int page, int size) {
        Page<CommentEntity> pageObj = commentCustomRepository.filter(filterDTO, page, size);
        List<CommentDTO> dtoList = new LinkedList<>();
        pageObj.forEach(entity -> {
            dtoList.add(convertToComment(entity));
        });
        return new PageImpl<>(dtoList, PageRequest.of(page, size), pageObj.getTotalElements());
    }

    private CommentDTO convertToComment(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setArticleId(entity.getArticleId());
        dto.setReplyId(entity.getReplyId());
        dto.setVisible(entity.getVisible());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdatedDate());
        dto.setContent(entity.getContent());
        return dto;
    }
}










