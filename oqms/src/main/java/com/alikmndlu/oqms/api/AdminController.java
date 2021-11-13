package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.dto.TeacherSelectionDto;
import com.alikmndlu.oqms.dto.UserWithoutPasswordDto;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/admin")
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
public class AdminController {

//    private final UserService userService;
//
//    @GetMapping("/users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<UserWithoutPasswordDto>> getUsers() {
//
//        // get all users
//        List<User> users = userService.getAllUsers();
//
//        List<UserWithoutPasswordDto> resultList = new ArrayList<>();
//
//        // transfer users to dto
//        users.forEach(user -> {
//            resultList.add(new UserWithoutPasswordDto(
//                    user.getId(),
//                    user.getName(),
//                    user.getUsername(),
//                    user.getRoles().stream().findFirst().get().getName()
//            ));
//        });
//
//        return ResponseEntity.ok().body(resultList);
//    }
//
//    @PutMapping("/users/edit-user")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String updateUser(@RequestBody UserWithoutPasswordDto updateUser) {
//        Optional<User> optionalUser = userService.findById(updateUser.getId());
//
//        if (optionalUser.isEmpty()) {
//            return "error";
//        }
//
//        User user = optionalUser.get();
//
//        //todo check unique username
////        if (!updateUser.getUsername().equals(user.getUsername()) || userService.findByUsername(updateUser.getUsername()).isPresent()){
////            return "not unique username!";
////        }
//
//        user.setName(updateUser.getName());
//        user.setUsername(updateUser.getUsername());
//        user.setRoles(new ArrayList<>());
//        userService.saveUser(user);
//        userService.addRoleToUser(user.getUsername(), updateUser.getRole());
//        return "success";
//    }
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
