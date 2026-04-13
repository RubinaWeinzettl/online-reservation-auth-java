package dev.rubinaw.auth;

import dev.rubinaw.auth.repository.JdbcUserRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet for handling user registration.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /**
     * Processes user registration requests.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException when servlet processing fails
     * @throws IOException when request or response handling fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        StringBuilder requestBody = new StringBuilder();

        try (var reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        JsonObject body;

        try (JsonReader jsonReader = Json.createReader(new StringReader(requestBody.toString()))) {
            body = jsonReader.readObject();
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid JSON");
            return;
        }

        String email = body.getString("email", "").trim();
        String password = body.getString("password", "").trim();

        if (email.isEmpty() || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("email and password must not be empty");
            return;
        }

        String emailNorm = email.trim().toLowerCase(Locale.ROOT);
        String userId = UUID.randomUUID().toString();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        JdbcUserRepository userRepository = new JdbcUserRepository();
        userRepository.save(userId, email, emailNorm, hashedPassword);

        resp.setContentType("text/plain");
        resp.getWriter().write("User registered successfully");
    }
}
