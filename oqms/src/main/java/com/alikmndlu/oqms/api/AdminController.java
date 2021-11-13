package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.UserWithoutPasswordDto;
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
    public ResponseEntity<List<UserWithoutPasswordDto>> getUsers() {
        // get all users
        List<User> users = userService.findAll();

        // transfer to UserWithoutPasswordDto and return the list
        return ResponseEntity.ok().body(
                users.stream()
                        .map(UserWithoutPasswordDto::userToUserWithoutPasswordDto)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/user/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> userDto(@RequestBody UserWithoutPasswordDto userDto) {
        User user = userService.findById(userDto.getId()).get();

        // Check new username is valid or not
        if (!userDto.getUsername().equals(user.getUsername()) && userService.findByUsername(userDto.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("This username is taken before!");
        }

        // Update user information and role
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setRoles(new ArrayList<>());
        userService.save(user);
        roleService.addRoleToUser(user.getUsername(), userDto.getRole());

        return ResponseEntity.ok().build();
    }
//
//
//    @GetMapping("/teacher-list")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<TeacherSelectionDto>> getTeachers() {
//        List<TeacherSelectionDto> teachers = new ArrayList<>();
//        userService.getTeachers().forEach(teacher -> {
//            teachers.add(new TeacherSelectionDto(
//                    teacher.getId(),
//                    teacher.getName(),
//                    teacher.getUsername()
//            ));
//        });
//        return ResponseEntity.ok().body(teachers);
//    }
}
