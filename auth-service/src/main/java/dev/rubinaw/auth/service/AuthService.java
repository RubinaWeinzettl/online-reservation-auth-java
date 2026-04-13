package dev.rubinaw.auth.service;

import dev.rubinaw.auth.dto.LoginRequest;
import dev.rubinaw.auth.model.User;
import dev.rubinaw.auth.repository.UserRepository;

/**
 * Contains the business logic for user login.
 */
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    /**
     * Creates a new auth service.
     *
     * @param userRepository the user repository
     * @param passwordService the password service
     */
    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param request the login request
     * @return {@code true} if the credentials are valid, otherwise {@code false}
     */
    public boolean login(LoginRequest request) {
        if (request == null) {
            return false;
        }

        String email = request.getEmail();
        String password = request.getPassword();

        if (email == null || email.isBlank()) {
            return false;
        }

        User user = userRepository.findByEmail(email.trim());
        if (user == null) {
            return false;
        }

        return passwordService.matches(password, user.getPasswordHash());
    }
}
