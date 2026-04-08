package dev.rubinaw.auth;

import java.io.StringReader;

import dev.rubinaw.auth.model.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 * REST resource for user registration.
 *
 * This endpoint accepts a JSON payload containing username and password,
 * validates the input, hashes the password using BCrypt, and returns
 * a simple confirmation response.
 *
 * NOTE:
 * - No persistence layer is implemented yet.
 * - This is a minimal example for learning purposes.
 */
@Path("/register")
public class RegisterResource {

    /**
     * Handles user registration requests.
     *
     * @param requestBody Raw JSON string containing username and password
     * @return HTTP response with status 200 (success) or 400 (invalid input)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(String requestBody) {

        JsonObject body;

        // Parse incoming JSON request
        try (JsonReader reader = Json.createReader(new StringReader(requestBody))) {
            body = reader.readObject();
        } catch (RuntimeException exception) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("username and password must be provided as valid JSON")
                    .build();
        }

        String username = body.getString("username", "").trim();
        String password = body.getString("password", "").trim();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("username and password must not be empty")
                    .build();
        }

        // Hash password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, hashedPassword);

        // NOTE: No persistence yet (in-memory only conceptually)

        return Response.ok("User registered successfully").build();
    }
}