package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.repository.RoleRepository;
import com.alikmndlu.oqms.repository.UserRepository;
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
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        return super.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findTeachers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().get(0).getName().equals("ROLE_TEACHER"))
                .collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            log.error("User {} Not Found In Database!", username);
            throw new UsernameNotFoundException(String.format("User %s Not Found In Database!", username));
        } else {
            log.info("User {} Found In Database!", username);
        }

        User u = user.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        u.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities
        );
    }
}
