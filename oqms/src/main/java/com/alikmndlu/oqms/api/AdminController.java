package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.*;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.RoleService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserIdNameUsernameRoleStatusDto>> getUsers() {

        // get all users
        List<User> users = userService.findUsers();

        // transfer to dto and back to client
        return ResponseEntity.ok().body(
                users.stream()
                        .map(UserIdNameUsernameRoleStatusDto::UserToUserIdNameUsernameRoleStatusDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/user/{user-id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserIdNameUsernameRoleStatusDto> getUserById(
            @PathVariable("user-id") Long id) {

        // get user
        List<User> users = userService.findUsers();
        User user = userService.findById(id).get();

        // transfer user to dto
        return ResponseEntity.ok().body(
                UserIdNameUsernameRoleStatusDto.UserToUserIdNameUsernameRoleStatusDto(user)
        );
    }

    // Update User
    @PutMapping("/user/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserIdNameUsernameRoleStatusDto userDto) {
        User user = userService.findById(userDto.getId()).get();
//        System.out.println("Status was : " + user.isActive());

        // Check new username is valid or not
        if (!userDto.getUsername().equals(user.getUsername()) && userService.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("This username is taken before!");
        }

        // Update user information and role
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
//        System.out.println("Status was : " + user.isActive());
        user.setActive(userDto.getStatus());
        userService.save(user);
//        user.setRoles(new ArrayList<>());
//        userService.save(user);
//        roleService.addRoleToUser(user.getUsername(), userDto.getRole());

        return ResponseEntity.ok().build();
    }


    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserIdNameUsernameDto>> getTeachers() {
        // GEt All Teachers
        List<User> teachers = userService.findTeachers();

        // Transfer Teacher to UserIdNameUsernameDto and return
        return ResponseEntity.ok().body(
                UserIdNameUsernameDto.UserListToUserIdNameUsernameDtoList(
                        teachers
                )
        );
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserIdNameUsernameDto>> getStudents() {
        // GEt All Teachers
        List<User> students = userService.findStudents();

        // Transfer Teacher to UserIdNameUsernameDto and return
        return ResponseEntity.ok().body(
                UserIdNameUsernameDto.UserListToUserIdNameUsernameDtoList(
                        students
                )
        );
    }

    @PostMapping("/search-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserIdNameUsernameRoleDto>> searchUsers(
            @RequestBody UserNameUsernameRoleDto userDto) {
        // Search For Users
        List<User> users = userService.searchUsers(userDto);

        // Transfer User to UserIdNameUsernameDto
        return ResponseEntity.ok().body(
                users.stream()
                        .map(UserIdNameUsernameRoleDto::UserToUserIdNameUsernameRoleDto)
                        .collect(Collectors.toList())
        );
    }
}
