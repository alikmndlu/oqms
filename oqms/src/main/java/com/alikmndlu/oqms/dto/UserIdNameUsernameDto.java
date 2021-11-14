package com.alikmndlu.oqms.dto;

import com.alikmndlu.oqms.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdNameUsernameDto {

    private Long id;

    private String name;

    private String username;

    public static List<UserIdNameUsernameDto> UserListToUserIdNameUsernameDtoList(List<User> users){
        return users.stream()
                .map(user -> new UserIdNameUsernameDto(
                        user.getId(),
                        user.getName(),
                        user.getUsername()
                ))
                .collect(Collectors.toList());
    }
}
