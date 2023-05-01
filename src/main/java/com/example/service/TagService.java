package com.example.service;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.tag.TagDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        Optional<TagEntity> optional = tagRepository.findByName(dto.getName());
        if (optional.isPresent()) {
            throw new AppBadRequestException("Tag name is Already use:) " + dto.getId());
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(TagDTO dto) {
        TagEntity entity = get(dto.getId());
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return true;
    }

    public TagEntity get(Integer id) {
        Optional<TagEntity> optional = tagRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Tag not found: " + id);
        }
        return optional.get();
    }

    public Boolean delete(Integer id) {
        TagEntity entity = get(id);
        tagRepository.delete(entity);
        return true;
    }

    public List<TagDTO> getAll() {
        List<TagDTO> dtoList = getdtoList(tagRepository.findAll());
        return dtoList;
    }

    public List<TagDTO> getdtoList(Iterable<TagEntity> entities) {
        List<TagDTO> dtoList = new LinkedList<>();
        entities.forEach(entity -> {
            TagDTO dto = new TagDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
