package com.example.dto.profile;

import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequestcustomDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileRole role;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
