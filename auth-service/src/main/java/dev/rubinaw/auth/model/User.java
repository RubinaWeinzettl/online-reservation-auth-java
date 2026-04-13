package dev.rubinaw.auth.model;

/**
 * Represents a user record required for authentication.
 */
public class User {

    private String userId;
    private String email;
    private String passwordHash;

    /**
     * Creates an empty user instance.
     */
    public User() {
    }

    /**
     * Creates a user instance with the fields needed for login.
     *
     * @param userId the user identifier
     * @param email the user email address
     * @param passwordHash the stored password hash
     */
    public User(String userId, String email, String passwordHash) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the user identifier.
     *
     * @return the user identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier.
     *
     * @param userId the user identifier
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the user email address.
     *
     * @return the user email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user email address.
     *
     * @param email the user email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the stored password hash.
     *
     * @return the stored password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the stored password hash.
     *
     * @param passwordHash the stored password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
