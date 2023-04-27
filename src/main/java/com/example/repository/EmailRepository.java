package com.example.repository;

import com.example.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailRepository extends CrudRepository<EmailEntity,Integer>, PagingAndSortingRepository<EmailEntity, Integer> {

    List<EmailEntity> findAllByEmailOrderByCreatedDataAsc(String email);

    List<EmailEntity> findByEmail(String email);

    List<EmailEntity> findByCreatedDataBetween(LocalDateTime beginDay , LocalDateTime createdData);
}
