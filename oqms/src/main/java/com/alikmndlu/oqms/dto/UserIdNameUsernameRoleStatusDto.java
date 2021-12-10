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
public class UserIdNameUsernameRoleStatusDto {

    private Long id;

    private String name;

    private String username;

    private String role;

    private Boolean status;

    public static UserIdNameUsernameRoleStatusDto UserToUserIdNameUsernameRoleStatusDto(User user) {
        return new UserIdNameUsernameRoleStatusDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRoles().get(0).getName(),
                user.isActive()
        );
    }
}
