package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndPasswordAndVisible(String login, String md5Hash, boolean b);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update profile set visible = :visible, status = :status where id = :id")
    Integer changeVisible(@Param("visible") Boolean visible, @Param("status") GeneralStatus status, @Param("id") Integer id);


    Optional<ProfileEntity> findByEmail(String email);
}
