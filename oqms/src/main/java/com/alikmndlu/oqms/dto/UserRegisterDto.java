package com.alikmndlu.oqms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "Name is mandatory!")
    private String name;

    @Size(min = 6, max = 12, message = "Username length must be between 6 and 12!")
    @NotBlank(message = "Username is mandatory!")
    private String username;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
            message = "Password must be minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character!")
    @NotBlank(message = "Password is mandatory!")
    private String password;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$",
            message = "PasswordConfirm must be minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character!")
    @NotBlank(message = "PasswordConfirm is mandatory!")
    private String passwordConfirm;

    @Pattern(regexp = "^ROLE_+(TEACHER|STUDENT)",
            message = "Only Student and Teacher roles are acceptable!")
    @NotBlank(message = "Role is mandatory!")
    private String role;
}
