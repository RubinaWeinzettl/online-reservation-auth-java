package dev.rubinaw.auth;

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

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet for handling user registration.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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

        String username = body.getString("username", "").trim();
        String password = body.getString("password", "").trim();

        if (username.isEmpty() || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("username and password must not be empty");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        resp.setContentType("text/plain");
        resp.getWriter().write("User registered successfully");
    }
}