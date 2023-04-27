package com.example.repository.profile;

import com.example.dto.profile.ProfileRequestcustomDTO;

import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> getAll() {
        Query query = this.entityManager.createQuery("SELECT p FROM ProfileEntity AS p");
        List profileList = query.getResultList();
        return profileList;
    }
    public List<ProfileEntity> filter(ProfileRequestcustomDTO filterDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append("Select p From ProfileEntity as p where id in (1,2,3,4,5) ");

        if (filterDTO.getName() != null) {
            builder.append(" and p.name = :name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            builder.append(" and p.surname = :surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getEmail() != null) {
            builder.append(" and p.email = :email");
            params.put("email", filterDTO.getEmail());
        }
        if (filterDTO.getPhone() != null) {
            builder.append(" and p.phone = :phone");
            params.put("phone", filterDTO.getPhone());
        }
        if (filterDTO.getRole() != null) {
            builder.append(" and p.role = :role");
            params.put("role", filterDTO.getRole());
        }
        if (filterDTO.getDateFrom() != null && filterDTO.getDateTo() != null) {
            builder.append(" and p.createdDate between :dateFrom and :dateTo ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getDateFrom(), LocalTime.MIN));
            params.put("dateTo", LocalDateTime.of(filterDTO.getDateTo(), LocalTime.MAX));
        } else if (filterDTO.getDateFrom() != null) {
            builder.append(" and p.createdDate >= :dateFrom ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getDateFrom(), LocalTime.MIN));
        } else if (filterDTO.getDateTo() != null) {
            builder.append(" and p.createdDate <= :dateTo ");
            params.put("dateTo", LocalDateTime.of(filterDTO.getDateTo(), LocalTime.MIN));
        }
        Query query = this.entityManager.createQuery(builder.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List profileList = query.getResultList();
        return profileList;
    }

}
