package com.example.dto.profile;

import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileRequestCustomDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileRole role;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
