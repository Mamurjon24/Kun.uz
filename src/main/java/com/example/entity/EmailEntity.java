package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "email")
@Entity
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    @Column(name = "email")
    private String email;
    @Column(name = "created_data")
    private LocalDateTime createdData;

}
