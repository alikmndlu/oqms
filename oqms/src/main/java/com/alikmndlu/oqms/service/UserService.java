package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.dto.UserNameUsernameRoleDto;
import com.alikmndlu.oqms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Long> {

    User insertNewUser(User user);

    Optional<User> findByUsername(String username);

    List<User> findTeachers();

    List<User> findStudents();

    List<User> findUsers();

    List<User> searchUsers(UserNameUsernameRoleDto userDto);

    User getLoggedInUser();
}
