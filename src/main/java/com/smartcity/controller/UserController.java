package com.smartcity.controller;

import com.smartcity.dto.UserDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/reset-password")
    public boolean updatePassword(@RequestBody String newPassword,
                                  @AuthenticationPrincipal User user) {

        Long userId = userService.findByEmail(user.getUsername()).getId();

        return userService.updatePassword(userId, newPassword);
    }

}
