package com.alikmndlu.oqms;

import com.alikmndlu.oqms.domain.Role;
import com.alikmndlu.oqms.domain.User;
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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner runner(UserService userService){
        return  args -> {
          userService.saveRole(new Role("ROLE_STUDENT"));
          userService.saveRole(new Role("ROLE_TEACHER"));
          userService.saveRole(new Role("ROLE_ADMIN"));

          userService.saveUser(new User("Ali Erfagh", "student", "password", new ArrayList<>()));
          userService.saveUser(new User("Ali Noori", "teacher", "password", new ArrayList<>()));
          userService.saveUser(new User("Ali Kmndlu", "admin", "password", new ArrayList<>()));

          userService.addRoleToUser("student", "ROLE_STUDENT");
          userService.addRoleToUser("teacher", "role_TEACHER");
          userService.addRoleToUser("admin", "ROLE_ADMIN");
        };
    }

}
