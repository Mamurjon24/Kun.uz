package com.example.service;

import com.example.dto.savedArticle.SavedArticleCreateRequestDTO;
import com.example.entity.SavedArticleEntity;
import com.example.exp.AppBadRequestException;
import com.example.mapper.SavedArticleMapper;
import com.example.repository.profile.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;

    public String create(SavedArticleCreateRequestDTO dto, Integer profileId) {
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        savedArticleRepository.save(entity);
        return "Article Saved";
    }

    public Boolean delete(Integer articleId) {
        SavedArticleEntity entity = get(articleId);
        savedArticleRepository.delete(entity);
        return true;
    }
    public SavedArticleEntity get(Integer id) {
        Optional<SavedArticleEntity> optional = savedArticleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Saved Articles not found: " + id);
        }
        return optional.get();
    }

    public SavedArticleMapper getAllSavedArticle() {
        SavedArticleMapper savedArticleMapper = savedArticleRepository.findAllSavedArticle();
        return savedArticleMapper;
    }
}
