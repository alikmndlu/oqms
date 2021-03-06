package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findTeachers();
}
