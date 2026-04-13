package dev.rubinaw.auth.repository;

import dev.rubinaw.auth.model.User;

/**
 * Provides user lookup operations for authentication.
 */
public interface UserRepository {

    /**
     * Finds a user by email address.
     *
     * @param email the email address to search for
     * @return the user when found, otherwise {@code null}
     */
    User findByEmail(String email);
}
