package com.smartcity.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.smartcity.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private static Logger logger = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    // TODO: Flushing expired tokens might be a good idea if the amount of users ever grows into the thousands range
    private final HashBiMap<String, HashedPasswordResetToken> emailTokenMappings;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public PasswordResetServiceImpl(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.emailTokenMappings = HashBiMap.create();
        // TODO: Perhaps generify EncryptionUtil or rename it to something like CustomPasswordEncoder
        // Going to use it here so as to not write duplicate code
        this.passwordEncoder = passwordEncoder;
    }

    static final String POSSIBLE_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final int TOKEN_LENGTH = 64;
    // TODO: FIX: Potential vulnerability; seed the PseudoRNG with a reliable True RNG
    static final Random RANDOM_GENERATOR = new Random();

    private String generateToken() {
        String result = "";

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            result += POSSIBLE_CHARS.charAt(RANDOM_GENERATOR.nextInt(POSSIBLE_CHARS.length()));
        }

        return result;
    }

    @Override
    public String sendResetPasswordMail(String email) {
        // Make sure this user actually exists
        // Will throw a NotFoundException if no such user exists
        UserDto user = userService.findByEmail(email);
        try {
            // Generate a random string of letters and numbers to be used as a token
            String token = generateToken();
            // Create a new HashedToken to safely store the token in the application
            HashedPasswordResetToken hashedToken = new HashedPasswordResetToken(token);
            // Map this token to the specified email address
            emailTokenMappings.put(email, hashedToken);
            // And finally, send the email
            emailService.sendSimpleMessage(
                    "SmartCity: Password reset requested",
                    "A password change was requested for the SmartCity account" +
                            " associated with this email address.\n" +
                            "If you didn't request a password change, please ignore this message.\n" +
                            "If you would like to change your password, please follow this link: \n" +
                            "http://localhost:4200/home/reset-password?token=" + token + "\n" +
                            "Best wishes, IF-098.Java",
                    email);
            return token;
        } catch (Exception e) {
            // TODO: Custom exceptions
            logger.error("Failed to generate a password reset token. Message: {}", e.getMessage());
            throw new UnknownError("Something went wrong. Please try again.");
        }
    }

    @Override
    public void resetPasswordViaToken(String token, String newPassword) {
        // Hash the received token
        HashedPasswordResetToken hashedToken = new HashedPasswordResetToken(token);
        // Obtain the inverse view of our HashBiMap, aka emailsByToken
        BiMap<HashedPasswordResetToken, String> inverse = emailTokenMappings.inverse();
        // Check whether the received token exists in our collection of tokens
        if (inverse.containsKey(hashedToken)) {
            // If it does, obtain the email associated with it
            String email = inverse.get(hashedToken);
            // Get the original token object
            HashedPasswordResetToken originalToken = emailTokenMappings.get(email);
            // and make sure it hasn't expired yet
            if (originalToken.isValid()) {
                // Update the password
                UserDto user = userService.findByEmail(email);
                userService.updatePassword(user.getId(), newPassword);
                emailTokenMappings.remove(email);
            } else {
                emailTokenMappings.remove(email);
                logger.error("Unsuccessful attempt to change a user's password. Token has expired.");
                throw new UnknownError("Invalid or expired token.");
            }
        } else {
            logger.error("Unsuccessful attempt to change a user's password. Token does not exist.");
            throw new UnknownError("Invalid or expired token.");
        }
    }

    public class HashedPasswordResetToken {
        // Tokens valid for half an hour
        static final long VALID_MILLISECONDS = 30 * 60 * 1000;

        private final String value;
        private final long createdAtMillis;

        public String getValue() {
            return value;
        }

        public HashedPasswordResetToken(String token) {
            value = passwordEncoder.encode(token);
            createdAtMillis = System.currentTimeMillis();
        }

        public Boolean isValid() {
            return (System.currentTimeMillis() - createdAtMillis) < VALID_MILLISECONDS;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashedPasswordResetToken that = (HashedPasswordResetToken) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }


}
