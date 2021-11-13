package com.alikmndlu.oqms;

import com.alikmndlu.oqms.model.Role;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.RoleService;
import com.alikmndlu.oqms.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class OqmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OqmsApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner runner(UserService userService, RoleService roleService) {
        return args -> {
            roleService.save(new Role("ROLE_STUDENT"));
            roleService.save(new Role("ROLE_TEACHER"));
            roleService.save(new Role("ROLE_ADMIN"));

            userService.save(new User("Ali Erfagh", "student", "password"));
            userService.save(new User("Ali Noori", "teacher", "password"));
            userService.save(new User("Ali Kmndlu", "admin", "password"));

            roleService.addRoleToUser("student", "ROLE_STUDENT");
            roleService.addRoleToUser("teacher", "ROLE_TEACHER");
            roleService.addRoleToUser("admin", "ROLE_ADMIN");
        };
    }

}
