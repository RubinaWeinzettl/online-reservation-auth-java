package dev.rubinaw.auth.service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Provides password hash verification.
 */
public class PasswordService {

    /**
     * Checks whether a plain text password matches a stored hash.
     *
     * @param plainPassword the plain text password
     * @param passwordHash the stored password hash
     * @return {@code true} if the password matches, otherwise {@code false}
     */
    public boolean matches(String plainPassword, String passwordHash) {
        if (plainPassword == null || plainPassword.isBlank()) {
            return false;
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            return false;
        }

        try {
            return BCrypt.checkpw(plainPassword, passwordHash);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
