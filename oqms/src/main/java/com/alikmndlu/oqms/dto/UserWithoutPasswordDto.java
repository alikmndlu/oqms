package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPasswordDto {

    private Long id;

    private String name;

    private String username;

    private String role;

    public static UserWithoutPasswordDto userToUserWithoutPasswordDto(User user) {
        return new UserWithoutPasswordDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRoles().stream()
                        .findFirst().get().getName()
        );
    }
}
