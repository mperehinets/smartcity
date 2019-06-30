package com.smartcity.controller;

import com.smartcity.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/forgotPassword")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/")
    public ResponseEntity forgotPassword(@RequestBody String mail) {
        passwordResetService.sendResetPasswordMail(mail);
        return ok(true);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        passwordResetService.resetPasswordViaToken(token, newPassword);
        return ok(true);
    }

}
