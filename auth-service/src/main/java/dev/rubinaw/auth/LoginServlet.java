package dev.rubinaw.auth;

import dev.rubinaw.auth.dto.LoginRequest;
import dev.rubinaw.auth.dto.LoginResponse;
import dev.rubinaw.auth.repository.JdbcUserRepository;
import dev.rubinaw.auth.service.AuthService;
import dev.rubinaw.auth.service.PasswordService;
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

/**
 * Handles login requests for the auth service.
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService =
            new AuthService(new JdbcUserRepository(), new PasswordService());

    /**
     * Processes login requests.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException when servlet processing fails
     * @throws IOException when request or response handling fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LoginRequest loginRequest = readLoginRequest(req);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (loginRequest == null || !authService.login(loginRequest)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            writeJsonResponse(resp, new LoginResponse("Invalid email or password"));
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        writeJsonResponse(resp, new LoginResponse("Login successful"));
    }

    /**
     * Reads the login request from the JSON body.
     *
     * @param req the HTTP request
     * @return the parsed login request or {@code null} if parsing fails
     * @throws IOException when request reading fails
     */
    private LoginRequest readLoginRequest(HttpServletRequest req) throws IOException {
        StringBuilder requestBody = new StringBuilder();

        try (var reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        try (JsonReader jsonReader = Json.createReader(new StringReader(requestBody.toString()))) {
            JsonObject body = jsonReader.readObject();
            return new LoginRequest(
                    body.getString("email", "").trim(),
                    body.getString("password", "")
            );
        } catch (RuntimeException exception) {
            return null;
        }
    }

    /**
     * Writes a JSON response body.
     *
     * @param resp the HTTP response
     * @param loginResponse the response payload
     * @throws IOException when response writing fails
     */
    private void writeJsonResponse(HttpServletResponse resp, LoginResponse loginResponse)
            throws IOException {
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("message", loginResponse.getMessage())
                .build();

        resp.getWriter().write(jsonResponse.toString());
    }
}
