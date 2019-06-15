package com.smartcity.controller;

import com.smartcity.domain.Role;
import com.smartcity.dto.UserDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() {
        return userService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findByEmail(@RequestParam("email") String email) {
        return userService.findByEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@Validated(ExistingRecord.class) @PathVariable("id") Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @PreAuthorize("hasAnyRole" +
            "(@securityConfiguration.getUserControllerDeleteUserAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @PreAuthorize("hasAnyRole" +
            "(@securityConfiguration.getUserControllerActivateUserAllowedRoles())")
    @PostMapping(value = "/activate/{id}")
    public boolean activateUser(@PathVariable("id") Long id){
        return userService.activate(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/reset-password")
    public boolean updatePassword(@RequestBody String newPassword,
                                  Authentication authentication) {

        Long userId = userService.findByEmail(authentication.getName()).getId();

        return userService.updatePassword(userId, newPassword);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/get-roles", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Role> getRolesByUserId(@PathVariable("id") Long id) {
        return userService.getRoles(id);
    }

    @PreAuthorize("hasAnyRole" +
            "(@securityConfiguration.getUserControllerSetRolesByUserIdAllowedRoles())")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/set-roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean setRolesByUserId(@PathVariable("id") Long userId, @RequestBody List<Long> newRolesIds) {
        return userService.setRoles(userId, newRolesIds);
    }

    @GetMapping(value = "/get-current", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto getCurrentUser(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

}
