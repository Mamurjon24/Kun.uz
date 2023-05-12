package com.example.controller;

import com.example.dto.tag.TagDTO;
import com.example.enums.ProfileRole;
import com.example.service.TagService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping({"/private", "/private/"})
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PostMapping(value = "/private/update")
    public ResponseEntity<?> update(@RequestBody @Valid TagDTO dto) {
        return ResponseEntity.ok(tagService.update(dto));
    }

    @PostMapping(value = "/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    @PostMapping(value = "/private/getList")
    public ResponseEntity<List<TagDTO>> getList() {
        List<TagDTO> list = tagService.getAll();
        return ResponseEntity.ok(list);
    }

}
