package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import com.example.repository.profile.ProfileRepository;
import com.example.util.MD5Util;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
//    @Autowired
//    private ProfileRepository profileRepository;
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

       /* String email = "admin@gmail.com";
        Optional<ProfileEntity> profileEntity = profileRepository.findByEmail(email);
        if (profileEntity.isEmpty()) {
            ProfileEntity entity = new ProfileEntity();
            entity.setName("Admin");
            entity.setSurname("Adminov");
            entity.setPhone("1");
            entity.setEmail(email);
            entity.setRole(ProfileRole.ADMIN);
            entity.setPassword(MD5Util.getMd5Hash("1"));
            entity.setStatus(GeneralStatus.ACTIVE);
            entity.setPrtId(1);
            profileRepository.save(entity);
            System.out.println("Amdin created");*/
    }
}

