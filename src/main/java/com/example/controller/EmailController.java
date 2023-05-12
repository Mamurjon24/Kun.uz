package com.example.controller;

import com.example.dto.articletype.ArticleTypeDTO;
import com.example.dto.email.EmailDTO;
import com.example.entity.EmailEntity;
import com.example.enums.ProfileRole;
import com.example.service.EmailService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/getByEmail/{email}")
    public ResponseEntity<List<EmailDTO>> getByEmail(@PathVariable("email") String email) {
        List<EmailDTO> list = emailService.findByEmail(email);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/getByCreatedDate/{createdDate}")
    public ResponseEntity<List<EmailDTO>> getByCreatedDate(@PathVariable("createdDate") LocalDate createdDate) {
        List<EmailDTO> list = emailService.findByCreatedData(createdDate);
        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/paging")
    public ResponseEntity<Page<EmailDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "2") int size) {
        Page<EmailDTO> response = emailService.pagingtion(page, size);
        return ResponseEntity.ok(response);
    }
}
