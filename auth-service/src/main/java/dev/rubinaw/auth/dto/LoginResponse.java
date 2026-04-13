package dev.rubinaw.auth.dto;

/**
 * Represents the JSON body of a login response.
 */
public class LoginResponse {

    private String message;

    /**
     * Creates an empty login response.
     */
    public LoginResponse() {
    }

    /**
     * Creates a login response with a message.
     *
     * @param message the response message
     */
    public LoginResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
