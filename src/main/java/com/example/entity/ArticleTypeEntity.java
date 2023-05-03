package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name="article_type")
@Entity()
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_eng")
    private String nameEng;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "prt_id")
    private Integer prtId;
    @Column(name = "name")
    private String name;

}
