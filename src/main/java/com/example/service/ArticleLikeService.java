package com.example.service;

import com.example.dto.articleLike.ArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.enums.LikeDislike;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public String check(ArticleLikeDTO dto,Integer profileId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.getLikeDisLikeStatus(dto.getArticleId());
        if (optional.isEmpty()) {
          return create(dto,profileId);
        }
        else  {
            return update(dto, optional.get(),profileId);
        }
    }

    public String create(ArticleLikeDTO dto,Integer profileId) {
        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setLikeDislike(dto.getLikeDislike());
        entity.setProfileId(profileId);
        articleLikeRepository.save(entity);
        return "Created";
    }

    public String update(ArticleLikeDTO dto, ArticleLikeEntity entity,Integer profileId) {
        LikeDislike dtoStatus = dto.getLikeDislike();
        LikeDislike entityStatus = entity.getLikeDislike();
        if (dtoStatus.equals(LikeDislike.LIKE) && entityStatus.equals(LikeDislike.LIKE) || dtoStatus.equals(LikeDislike.DISLIKE) && entityStatus.equals(LikeDislike.DISLIKE)) {
            entity.setLikeDislike(LikeDislike.NONE);
        }
        if (dtoStatus.equals(LikeDislike.LIKE) && entityStatus.equals(LikeDislike.DISLIKE)) {
            entity.setLikeDislike(LikeDislike.LIKE);
        }
        if (dtoStatus.equals(LikeDislike.DISLIKE) && entityStatus.equals(LikeDislike.LIKE)) {
            entity.setLikeDislike(LikeDislike.DISLIKE);
        }
        entity.setProfileId(profileId);
        entity.setArticleId(dto.getArticleId());
        articleLikeRepository.save(entity);
        return "Updated !";
    }


}
