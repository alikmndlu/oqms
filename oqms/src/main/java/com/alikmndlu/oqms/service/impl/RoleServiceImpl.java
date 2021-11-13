package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.Role;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.RoleRepository;
import com.alikmndlu.oqms.repository.UserRepository;
import com.alikmndlu.oqms.service.RoleService;
import com.alikmndlu.oqms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository>
        implements RoleService {

    private final RoleRepository roleRepository;

    private final UserService userService;

    public RoleServiceImpl(RoleRepository roleRepository, UserService userService) {
        super(roleRepository);
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userService.findByUsername(username).get();
        Role role = roleRepository.findByName(roleName).get();
        log.info("Add Role {} To User {}", role.getName(), user.getUsername());
        user.getRoles().add(role);
    }
}
