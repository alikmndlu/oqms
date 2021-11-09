package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.domain.Role;
import com.alikmndlu.oqms.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUser(String username);
    List<User> getAllUsers();
}
