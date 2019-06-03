package com.smartcity.controller;

import com.smartcity.dto.UserDto;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto registerUser(@Validated(NewRecord.class) @RequestBody UserDto user) {
        user.setActive(true);
        return userService.create(user);
    }

}
