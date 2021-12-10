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
public class UserIdNameUsernameRoleDto {

    private Long id;

    private String name;

    private String username;

    private String role;

    public static UserIdNameUsernameRoleDto UserToUserIdNameUsernameRoleDto(User user) {
        return new UserIdNameUsernameRoleDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRoles().get(0).getName()
        );
    }
}
