package com.example.dto.profile;

import com.example.enums.GeneralStatus;
import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    @NotBlank(message = "Id must have some value")
    private Integer id;
    @NotEmpty(message = "Name qani")
    private String name;
    @NotEmpty(message = "Surname qani")
    private String surname;
    @NotBlank(message = "Email must have some value")
    private String email;
    @NotBlank(message = "Phone not Null")
    private String phone;
    @NotBlank(message = "Password must have some value")
    private String password;
    @NotNull(message = "Admin must Give Role")
    private ProfileRole role;
    @NotBlank(message = "Give Profile Status")
    private GeneralStatus status;
    @NotBlank(message = "Visible must be Given")
    private Boolean visible;
    private String photoId;

}
