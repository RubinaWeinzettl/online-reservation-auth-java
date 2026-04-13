package dev.rubinaw.auth.dto;

/**
 * Represents the JSON body of a login request.
 */
public class LoginRequest {

    private String email;
    private String password;

    /**
     * Creates an empty login request.
     */
    public LoginRequest() {
    }

    /**
     * Creates a login request with email and password.
     *
     * @param email the user email address
     * @param password the plain text password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
     * Returns the plain text password.
     *
     * @return the plain text password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the plain text password.
     *
     * @param password the plain text password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
