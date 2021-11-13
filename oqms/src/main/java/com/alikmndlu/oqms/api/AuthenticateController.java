package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.UserRegisterDto;
import com.alikmndlu.oqms.model.User;
import com.alikmndlu.oqms.service.RoleService;
import com.alikmndlu.oqms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticateController {

    private final UserService userService;

    private final RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRegisterDto userDto, BindingResult result) {
        StringBuilder errorMessage = new StringBuilder("Error: \n");

        // Check RegisterDto Validations
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError e : errors) {
                errorMessage.append("- ").append(e.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        // Password Equality Check
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            return ResponseEntity.badRequest().body(errorMessage.append("- Passwords are not equal!"));
        }

        // Unique Username Check
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(errorMessage.append("- Username is not unique!"));
        }

        // Save New User In Database
        User user = userService.save(
                new User(
                        userDto.getName(),
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        // Assign Role To Registered User
        roleService.addRoleToUser(user.getUsername(), userDto.getRole());


        log.info("User {} has been registered!", userDto.getUsername());

        // Return User
        return ResponseEntity.ok().body(user);
    }
}
