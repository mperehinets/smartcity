package com.smartcity.service;

public interface PasswordResetService {
    String sendResetPasswordMail(String email);

    void resetPasswordViaToken(String token, String newPassword);
}
